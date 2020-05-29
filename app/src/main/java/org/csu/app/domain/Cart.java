package org.csu.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Cart implements Serializable {

  private static final long serialVersionUID = 8329559983943337176L;

//  private final Map<String, Item> itemMap = Collections.synchronizedMap(new HashMap<>());
  private final List<Item> itemList = new ArrayList<>();

  public Iterator<Item> getItems() {
    return itemList.iterator();
  }

  public List<Item> getItemList() {
    return itemList;
  }

  public int getNumberOfItems() {
    return itemList.size();
  }

  public Iterator<Item> getAllItems() {
    return itemList.iterator();
  }
//  public boolean containsItemId(String itemId) {
//    return itemMap.containsKey(itemId);
//  }


  public void addItem(Item item) {
//    Item Item = itemMap.get(item.getItemId());
//    if (Item == null) {
//      Item = new Item();
//      Item.setItem(item);
//      Item.setQuantity(0);
//      Item.setInStock(isInStock);
//      itemMap.put(item.getItemId(), Item);
//      itemList.add(Item);
//    }
//    Item.incrementQuantity();
  }

//  public Item removeItemById(String itemId) {
//    Item Item = itemMap.remove(itemId);
//    if (Item == null) {
//      return null;
//    } else {
//      itemList.remove(Item);
//      return Item.getItem();
//    }
//  }

//  public void incrementQuantityByItemId(String itemId) {
//    Item Item = itemMap.get(itemId);
//    Item.incrementQuantity();
//  }
//
//  public void setQuantityByItemId(String itemId, int quantity) {
//    Item Item = itemMap.get(itemId);
//    Item.setQuantity(quantity);
//  }

//  public BigDecimal getSubTotal() {
//    return itemList.stream()
//        .map(Item -> Item.getItem().getListPrice().multiply(new BigDecimal(Item.getQuantity())))
//        .reduce(BigDecimal.ZERO, BigDecimal::add);
//  }

}
