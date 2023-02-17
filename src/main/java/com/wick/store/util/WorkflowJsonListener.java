package com.wick.store.util;

import cn.hutool.core.text.CharPool;
import com.alibaba.fastjson.JSON;
import com.wick.store.domain.Dto.WorkflowFlatNodeDto;
import com.wick.store.domain.Dto.WorkflowHandleNodeDto;
import com.wick.store.util.antlr.JSONBaseListener;
import com.wick.store.util.antlr.JSONLexer;
import com.wick.store.util.antlr.JSONParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;
import java.util.stream.Collectors;

public class WorkflowJsonListener extends JSONBaseListener {


    private Stack<String> parallelSerialStack = new Stack<>();

    private List<WorkflowFlatNodeDto> flatWorkflow2List = new ArrayList<>();

    private List<String> nodeIdList = new ArrayList<>();

    private HashMap<String, WorkflowHandleNodeDto> handleNodeDtoHashMap = new HashMap();

    public WorkflowJsonListener(String workflowJson, boolean isBase64Format) {
        super();
        if (isBase64Format){
            Base64.Decoder dec = Base64.getDecoder();
            byte[] workflowJsonBytes = dec.decode(workflowJson);
            workflowJson = new String(workflowJsonBytes);
        }
        CharStream input = CharStreams.fromString(workflowJson);
        JSONLexer lexer = new JSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        ParseTree tree = parser.json();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, tree);
    }

    public Set<String> getHandleNodeUserIdsSet() {
        return handleNodeUserIdsSet;
    }

    private Set<String> handleNodeUserIdsSet = new HashSet<>();

    public boolean isNodeIdNotUnique(){
        Set<String> nodeIdSet = new HashSet<>();
        for (String nodeId:nodeIdList) {
            if (nodeIdSet.contains(nodeId)){
                return true;
            } else {
                nodeIdSet.add(nodeId);
            }
        }
        return false;
    }

    public List<WorkflowHandleNodeDto> nextPendingHandleNode(Set<String> handledNodeIdSet) {
        System.out.println("XXXXXXXXXXXXXXXXX-Start-workflow-flat-XXXXXXXXXXXXXXXXXXX");
        for (WorkflowFlatNodeDto flatNode : flatWorkflow2List) {
            System.out.println(flatNode.toString());
        }
        System.out.println("XXXXXXXXXXXXXXXXX-End-workflow-flat-XXXXXXXXXXXXXXXXXXX");

        List<WorkflowHandleNodeDto> nextPendingHandleNodeList = new ArrayList<>();
        for (String nodeId : nodeIdList) {
            if (handledNodeIdSet.contains(nodeId)) {
                continue;
            }
            for (int i = flatWorkflow2List.size() - 1; i >= 0; i--) {
                if (nodeId.equals(flatWorkflow2List.get(i).getNodeId())) {
                    WorkflowFlatNodeDto currentFlatNode = flatWorkflow2List.get(i);
                    String pendingHandleNodeId = findByFlatNodeLevel(currentFlatNode.getFlatNodeLevel(), nodeId, i, handledNodeIdSet);
                    if (!pendingHandleNodeId.isEmpty()) {
                        nextPendingHandleNodeList.add(handleNodeDtoHashMap.get(pendingHandleNodeId));
                    }
                    break;
                }
            }
        }
        return nextPendingHandleNodeList;
    }

    private String findByFlatNodeLevel(String flatNodeLevel, String findNodeId, int currentFlatNodeIndex, Set<String> handledNodeIdSet) {
        if (flatNodeLevel.equals("parallelNode")) {
            return findNodeId;
        }

        if (flatNodeLevel.endsWith("serialNode")) {

            if ((flatWorkflow2List.get(currentFlatNodeIndex - 1).getFlatNodeLevel().equals(flatNodeLevel) &&
                    flatWorkflow2List.get(currentFlatNodeIndex - 1).getNodeId().isEmpty())
                    && flatWorkflow2List.get(currentFlatNodeIndex - 2).getFlatNodeLevel().equals(flatNodeLevel.substring(0,flatNodeLevel.length() - ",serialNode".length()))) {
                //return findByFlatNodeLevel(flatNodeLevel.replace(",serialNode", ""), findNodeId, currentFlatNodeIndex - 2, handledNodeIdSet);
                return findByFlatNodeLevel(flatNodeLevel.substring(0,flatNodeLevel.length() - ",serialNode".length()), findNodeId, currentFlatNodeIndex - 2, handledNodeIdSet);
            }

            for (int i = currentFlatNodeIndex; i >= 0; i--) {
                if (flatWorkflow2List.get(i).getNodeId().equals(findNodeId)) {
                    continue;
                }

                if (flatWorkflow2List.get(i ).getFlatNodeLevel().equals(flatNodeLevel)
                        && flatWorkflow2List.get(i - 1).getFlatNodeLevel().equals(flatNodeLevel.substring(0,flatNodeLevel.length() - ",serialNode".length()))) {
                    //return findByFlatNodeLevel(flatNodeLevel.replace(",serialNode", ""), findNodeId, i, handledNodeIdSet);
                    return findByFlatNodeLevel(flatNodeLevel.substring(0,flatNodeLevel.length() - ",serialNode".length()), findNodeId, i, handledNodeIdSet);
                }

                if (flatWorkflow2List.get(i).getFlatNodeLevel().startsWith(flatNodeLevel)) {
                    if (!flatWorkflow2List.get(i).getNodeId().isEmpty() && !handledNodeIdSet.contains(flatWorkflow2List.get(i).getNodeId())) {
                        return "";
                    }
                } else {
                    break;
                }
            }
            return findNodeId;
        }

        if (flatNodeLevel.endsWith("parallelNode")) {
            //String newFlatNodeLevel = flatNodeLevel.replace(",parallelNode", "");
            String newFlatNodeLevel = flatNodeLevel.substring(0,flatNodeLevel.length() - ",parallelNode".length());
            int newFlatNodeIndex = 0;
            for (int i = currentFlatNodeIndex; i >= 0; i--) {
                if (flatWorkflow2List.get(i).getFlatNodeLevel().equals(newFlatNodeLevel) && flatWorkflow2List.get(i + 1).getFlatNodeLevel().equalsIgnoreCase(flatNodeLevel)) {
                    newFlatNodeIndex = i + 1;
                    break;
                }
            }
            return findByFlatNodeLevel(newFlatNodeLevel, findNodeId, newFlatNodeIndex, handledNodeIdSet);
        }
        System.out.println("??????????????????");
        return "";
    }


    @Override
    public void enterPair(JSONParser.PairContext ctx) {
        //System.out.println("~enterPair:"+ctx.STRING().getText());
        String currentPair = stripQuotes(ctx.STRING().getText());
        if (currentPair.equals("parallelNode") || currentPair.equals("serialNode")) {
            parallelSerialStack.push(currentPair);
            //System.out.println(parallelSerialStack);
            flatWorkflow2List.add(new WorkflowFlatNodeDto(parallelSerialStack.stream().collect(Collectors.joining(",")), ""));
        }

        if (currentPair.equals("handleNode")) {
            //System.out.println(ctx.getText());
            WorkflowHandleNodeDto handleNode = JSON.parseObject(ctx.getText().replaceFirst("\"handleNode\":", ""), WorkflowHandleNodeDto.class);
            //System.out.println(handleNode);
            nodeIdList.add(handleNode.getNodeId());
            handleNodeDtoHashMap.put(handleNode.getNodeId(), handleNode);
            for (String userId : handleNode.getUserIdList()) {
                handleNodeUserIdsSet.add(userId);
            }
            // 传入node set判断是否存在。
            if (flatWorkflow2List.size() > 0) {
                String lastFlatNodeLevel = flatWorkflow2List.get(flatWorkflow2List.size() - 1).getFlatNodeLevel();
                flatWorkflow2List.add(new WorkflowFlatNodeDto(lastFlatNodeLevel, handleNode.getNodeId()));
            }
        }

    }

    @Override
    public void exitPair(JSONParser.PairContext ctx) {
        //System.out.println("*exitPair:"+ctx.STRING().getText());
        String currentPair = stripQuotes(ctx.STRING().getText());
        if (!parallelSerialStack.isEmpty() && currentPair.equals(parallelSerialStack.peek())) {
            parallelSerialStack.pop();
            flatWorkflow2List.add(new WorkflowFlatNodeDto(parallelSerialStack.stream().collect(Collectors.joining(",")), ""));
            //System.out.println(parallelSerialStack);
        }

    }


    /**
     * 去除字符串包裹着的双引号
     */
    private static String stripQuotes(String s) {
        if (s == null || s.charAt(0) != CharPool.DOUBLE_QUOTES) {
            return s;
        }
        return s.substring(1, s.length() - 1);
    }

}