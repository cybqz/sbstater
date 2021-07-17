package com.cyb.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author CYB
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTreeIndex {

  private Integer layer;
  private Integer order;
  private Integer parentOrder;

  public static FileTreeIndex getNew(FileTreeIndex index){
    FileTreeIndex fileTreeIndex = new FileTreeIndex();
    BeanUtils.copyProperties(index, fileTreeIndex);
    return fileTreeIndex;
  }

  /**
   * 是否相同
   * @param index
   * @return
   */
  public boolean equals(FileTreeIndex index){
    if(this.layer.equals(index.getLayer()) &&
       this.order.equals(index.getOrder()) &&
       this.parentOrder.equals(index.getParentOrder())){
      return true;
    }
    return false;
  }

  /**
   * 更新
   * @param layer
   * @param order
   * @param parentOrder
   */
  public void update(Integer layer, Integer order, Integer parentOrder){
    if(layer < this.layer){
      //回退了一级
      this.order = order + 1;
    }else if(layer > this.layer){
      //前进了一级
      this.order = 0;
    }else {
      //还是在当前目录
      this.order += 1;
    }
    this.setLayer(layer);
    this.setParentOrder(parentOrder);
  }
}
