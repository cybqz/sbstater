package com.cyb.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.cyb.common.result.FileTree;
import com.cyb.common.result.FileTreeIndex;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * DirectoryUtil
 * @author CYB
 */
@AllArgsConstructor
public class DirectoryUtil {

    /**
     * 根路径
     */
    private String rootPath;

    /**
     * 是否打印日志
     */
    private boolean printLog;

    /**
     * 是否打印耗时
     */
    private boolean printCostTime;

    /**
     * 最大递归深度
     */
    private int maxRecursionDeep = -1;

    private static FileTree FILE_TREE = new FileTree();
    private static Map<String, Integer> PATH_ORDER_MAP = new HashMap<String, Integer>();
    private static Map<FileTreeIndex, FileTree> FILE_TREE_INDEX_MAP = new TreeMap<FileTreeIndex, FileTree>();

    /**
     * 递归遍历根目录及其一下所有文件结构，
     * 生成文件树结构
     */
    public void listFileTree(){
        File file = new File(this.rootPath);
        long start = System.currentTimeMillis();
        recursionFileTree(file, new FileTreeIndex(0,0,-1));
        if(printCostTime){
            int cost = (int) ((System.currentTimeMillis() - start)/1000);
            System.out.println(String.format("ListFileTree Cost: %ss", cost));
        }
    }

    /**
     * 打印结果
     */
    public void printResult(){
        FILE_TREE_INDEX_MAP.forEach((k,v)->{
            String subdirectorySize = v.getSubdirectory()==null?"NULL":String.valueOf(v.getSubdirectory().size());
            String absPath = v.getAbsPath()==null?"NULL":v.getAbsPath();
            System.out.println(k.toString() + "\t" + subdirectorySize + "\t" + absPath);
        });
    }

    /**
     * 递归遍历根目录及其一下所有文件结构，生成文件树结构
     * @param currentFile 当前文件或目录File对象
     * @param index 坐标
     */
    private void recursionFileTree(File currentFile, FileTreeIndex index) {
        if(printLog){
            System.out.println(currentFile.getPath() + "\t" + JSONObject.toJSONString(index));
        }
        if(!FILE_TREE_INDEX_MAP.containsKey(index)){

            if(index.getParentOrder().equals(-1)){
                //第一次运行
                PATH_ORDER_MAP.put(rootPath, index.getOrder());
                FILE_TREE.initWithFile(currentFile, index);
                FILE_TREE_INDEX_MAP.put(FileTreeIndex.getNew(index), FILE_TREE);
            }else {
                FileTree parentFileTree = getFileTreeByPath(FILE_TREE, currentFile.getParent(), null);
                if(null != parentFileTree){
                    addElement(currentFile, parentFileTree, index);
                }else {
                    return;
                }
            }

            //如果当前层级到组大递归深度，直接返回
            if(maxRecursionDeep > 0 && index.getLayer().compareTo(maxRecursionDeep) >= 0){
                return;
            }

            //遍历子目录并回调处理
            if(currentFile.isDirectory()){
                File[] files = currentFile.listFiles();
                if(null != files && files.length > 0){
                    for(File file : files){

                        Integer parentOrder = 0;
                        String path = file.getPath();
                        String parent = file.getParent();
                        Integer layer = MyStringUtil.count(path.replace(rootPath,""), "\\");

                        if(PATH_ORDER_MAP.containsKey(parent)){
                            parentOrder = PATH_ORDER_MAP.get(parent);
                        }
                        Integer order = getLastLayerOrder(layer);
                        index.update(layer, order, parentOrder);
                        PATH_ORDER_MAP.put(path, index.getOrder());
                        recursionFileTree(file, index);
                    }
                }
            }
        }
    }

    /**
     * 获取当前层最新顺序
     * @param layer
     * @return
     */
    private Integer getLastLayerOrder(Integer layer){
        AtomicReference<Integer> order = new AtomicReference<>(0);
        PATH_ORDER_MAP.forEach((k,v)->{
            if(layer.equals(MyStringUtil.count(k.replace(rootPath,""), "\\"))){
                if(v.compareTo(order.get())>0){
                    order.set(v);
                }
            }
        });
        return order.get();
    }

    /**
     * 根据路径获取FileTree
     * @param fileTree
     * @param layer
     * @return
     */
    private FileTree getFileTreeByPath(FileTree fileTree, String path, Integer layer){
        if(null != fileTree){
            if (null == layer){
                layer = MyStringUtil.count(path.replace(rootPath,""), "\\");
            }
            if(isTheFileTree(fileTree, path, layer)){
                return fileTree;
            }
            if(!CollectionUtils.isEmpty(fileTree.getSubdirectory())){
                Integer finalLayer = layer;
                FileTree fileTreeTmp = fileTree.getSubdirectory()
                        .stream()
                        .filter(d -> isTheFileTree(fileTree, path, finalLayer))
                        .findAny().orElse(null);
                if(null != fileTreeTmp){
                    return fileTreeTmp;
                }
                for(FileTree ft : fileTree.getSubdirectory()){
                    FileTree ftt = getFileTreeByPath(ft, path, layer);
                    if(null == ftt){
                        continue;
                    }
                    return ftt;
                }
            }
        }
        return null;
    }

    /**
     * 添加节点
     * @param currentFile
     * @param parentFileTree
     * @param index
     */
    private void addElement(File currentFile, FileTree parentFileTree, FileTreeIndex index){
        List<FileTree> subdirectory = (parentFileTree.getSubdirectory()==null?new ArrayList<FileTree>():parentFileTree.getSubdirectory());
        FileTree fileTree = FileTree.getWithFile(currentFile, index);
        subdirectory.add(fileTree);
        parentFileTree.setSubdirectory(subdirectory);
        FILE_TREE_INDEX_MAP.put(FileTreeIndex.getNew(index), fileTree);
    }

    /**
     * 根据层数及路径查询FileTree
     * @param fileTree
     * @param path
     * @param layer
     * @return
     */
    private boolean isTheFileTree(FileTree fileTree, String path, Integer layer){
        boolean sameLayer = layer.equals(fileTree.getIndex().getLayer());
        boolean samePath = path.equals(fileTree.getAbsPath());
        return (sameLayer && samePath);
    }
}
