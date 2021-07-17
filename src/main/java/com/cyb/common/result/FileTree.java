package com.cyb.common.result;

import java.io.File;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author CYB
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTree {

  private String name;
  private String parent;
  private String absPath;
  private FileTreeIndex index;
  private boolean isDirectory;
  private List<FileTree> subdirectory;

  public static FileTree getWithFile(File file, FileTreeIndex index) {
    FileTree fileTree = new FileTree();
    fileTree.initWithFile(file, index);
    return fileTree;
  }

  public void initWithFile(File file, FileTreeIndex index){
    this.name = file.getName();
    this.parent = file.getParent();
    this.absPath = file.getAbsolutePath();
    this.index = FileTreeIndex.getNew(index);
    this.isDirectory = file.isDirectory();
  }
}
