package com.cyb.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.cyb.common.result.FileTree;
import com.cyb.common.result.FileTreeIndex;
import org.springframework.util.CollectionUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DirectoryUtil
 * @author CYB
 */
public class DirectoryUtil {

    private String rootPath;
    private static FileTree FILE_TREE = new FileTree();
    private static Map<String, Integer> PATH_ORDER_MAP = new HashMap<String, Integer>();
    private static List<FileTreeIndex> FILE_TREE_INDEX_LIST = new ArrayList<FileTreeIndex>();

    public static void main(String[] args) {
        DirectoryUtil directoryUtil = new DirectoryUtil();
        File file = new File("F:\\Tencent");
        directoryUtil.list(file, new FileTreeIndex(0,0,-1));
        System.out.println(JSONObject.toJSONString(FILE_TREE));
    }
    /**
     * 列出该目录下所有子目录及文件
     * @param currentFile 当前文件或目录File对象
     * @param index 坐标
     */
    public void list(File currentFile, FileTreeIndex index) {
        System.out.println(currentFile.getPath() + "\t" + JSONObject.toJSONString(index));
        if(!isListed(index)){

            //标记已处理
            FILE_TREE_INDEX_LIST.add(FileTreeIndex.getNew(index));
            if(index.getParentOrder().equals(-1)){
                //第一次运行
                rootPath = currentFile.getPath();
                PATH_ORDER_MAP.put(rootPath, index.getOrder());
                FILE_TREE.initWithFile(currentFile, index);
            }else {
                FileTree parentFileTree = getFileTreeByPath(FILE_TREE, currentFile.getParent(), null);
                if(null != parentFileTree){
                    addElement(currentFile, parentFileTree, index);
                }else {
                    return;
                }
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
                        Integer order = getLastLoopOrder(layer);
                        index.update(layer, order, parentOrder);
                        PATH_ORDER_MAP.put(path, index.getOrder());
                        list(file, index);
                    }
                }
            }
        }
    }

    /**
     * 是否已处理
     * @param index
     * @return
     */
    private boolean isListed(FileTreeIndex index) {
        for(FileTreeIndex fti : FILE_TREE_INDEX_LIST){
            if(fti.equals(index)){
                return true;
            }
        }
        return false;
    }

    private Integer getLastLoopOrder(Integer layer){
        Integer order = 0;
        for(FileTreeIndex fti : FILE_TREE_INDEX_LIST){
            if(fti.getLayer().equals(layer)){
                order = fti.getOrder();
            }
        }
        return order;
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
        subdirectory.add(FileTree.getWithFile(currentFile, index));
        parentFileTree.setSubdirectory(subdirectory);
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
