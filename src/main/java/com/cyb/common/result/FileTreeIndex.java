package com.cyb.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author CYB
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTreeIndex implements Comparable{

  private Integer layer;
  private Integer order;
  private Integer parentOrder;

  public static FileTreeIndex getNew(FileTreeIndex index){
    return new FileTreeIndex(index.getLayer(), index.getOrder(), index.getParentOrder());
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

  @Override
  public String toString(){
    return this.layer+"_"+this.order+"_"+this.parentOrder;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((layer == null) ? 0 : layer.hashCode());
    result = prime * result + ((order == null) ? 0 : order.hashCode());
    result = prime * result + ((parentOrder == null) ? 0 : parentOrder.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if (null == obj){
      return false;
    }
    if(getClass() != obj.getClass()){
      return false;
    }

    FileTreeIndex fti = (FileTreeIndex) obj;
    if(layer == null) {
      if (fti.layer != null){
        return false;
      }
    }else if(!layer.equals(fti.layer)){
      return false;
    }

    if(order == null) {
      if (fti.order != null){
        return false;
      }
    }else if(!order.equals(fti.order)){
      return false;
    }

    if(parentOrder == null) {
      if (fti.parentOrder != null){
        return false;
      }
    }else if(!parentOrder.equals(fti.parentOrder)){
      return false;
    }
    return true;
  }

  /**
   * TreeMap需要实现compareTo方法，用以比较大小，
   * 如果当前比较直相等不进行其他比较的话，则会出现键值不对应的情况。
   * @param o
   * @return
   */
  @Override
  public int compareTo(Object o) {
    FileTreeIndex fti = (FileTreeIndex) o;
    int compareLayer = this.layer.compareTo(fti.getLayer());
    if(compareLayer != 0){
      return compareLayer;
    }
    int compareOrder = this.order.compareTo(fti.getOrder());
    if(compareOrder != 0){
      return compareOrder;
    }
    int compareParentOrder = this.parentOrder.compareTo(fti.getParentOrder());
    return compareParentOrder;
  }
}
