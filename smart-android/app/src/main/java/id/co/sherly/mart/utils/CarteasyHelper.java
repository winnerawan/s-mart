package id.co.sherly.mart.utils;

import android.content.Context;
import android.util.Log;

import com.carteasy.v1.lib.Carteasy;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import id.co.sherly.mart.data.model.Item;
import id.co.sherly.mart.data.model.ItemStock;

public class CarteasyHelper {

    public void getCarts(Context context, Carteasy cs) {
        Map<Integer, Map> data;
        data = cs.ViewAll(context);

        if (data==null) {
            return;
        }
        for (Map.Entry<Integer, Map> entry : data.entrySet()) {
            //get the Id
//            Log.e("Key: ", entry.getKey().toString());
//            Log.e("Value: ", entry.getValue().toString());

            //Get the items tied to the Id
            Map<String, String> innerdata = entry.getValue();
            for (Map.Entry<String, String> innerentry : innerdata.entrySet()) {
//                Log.e("Inner Key: ",innerentry.getKey());
//                Log.e("Inner Value: ",innerentry.getValue());
            }
        }
    }



    public List<Item> getCartsToItems(Context context, Carteasy cs) {
        Map<Integer, Map> data;
        List<Item> items = new ArrayList<Item>();
        data = cs.ViewAll(context);

        if (data==null) {
            return List.of();
        }
        for (Map.Entry<Integer, Map> entry : data.entrySet()) {
            //get the Id
//            Log.e("Key: ", entry.getKey().toString());
//            Log.e("Value: ", entry.getValue().toString());

            //Get the items tied to the Id
            Map<String, String> innerdata = entry.getValue();
            for (Map.Entry<String, String> innerentry : innerdata.entrySet()) {
//                Log.e("Inner Key: ",innerentry.getKey());
//                Log.e("Inner Value: ",innerentry.getValue());
            }
            items.add(keyValueToItem(entry.getValue()));
        }
//        Log.e("LOG", "items:"+ new Gson().toJson(items));
        return items;
    }

    public List<ItemStock> getCartsToItemStocks(Context context, Carteasy cs) {
        Map<Integer, Map> data;
        List<ItemStock> items = new ArrayList<ItemStock>();
        data = cs.ViewAll(context);

        if (data==null) {
            return List.of();
        }
        for (Map.Entry<Integer, Map> entry : data.entrySet()) {
            //get the Id
//            Log.e("Key: ", entry.getKey().toString());
//            Log.e("Value: ", entry.getValue().toString());

            //Get the items tied to the Id
            Map<String, String> innerdata = entry.getValue();
            for (Map.Entry<String, String> innerentry : innerdata.entrySet()) {
//                Log.e("Inner Key: ",innerentry.getKey());
//                Log.e("Inner Value: ",innerentry.getValue());
            }
            items.add(keyValueToItemStock(entry.getValue()));
        }
        Log.e("LOG", "items:"+ new Gson().toJson(items));
        return items;
    }

    private ItemStock keyValueToItemStock(Map<String, String> innerdata) {
        ItemStock item = new ItemStock();
        item.setItemId(innerdata.get("id"));
        item.setImage(innerdata.get("image"));
        item.setName(innerdata.get("name"));
        item.setCategoryId(Integer.parseInt(innerdata.get("category")));
        item.setQuantity(Integer.parseInt(innerdata.get("quantity")));
        item.setSellingPrice(innerdata.get("selling_price"));
        item.setRecyclerViewPosition(Integer.parseInt(innerdata.get("recyclerViewPosition")));
        return item;
    }

    private Item keyValueToItem(Map<String, String> innerdata) {
        Item item = new Item();
        item.setId(innerdata.get("id"));
        item.setImage(innerdata.get("image"));
        item.setName(innerdata.get("name"));
        item.setCategoryId(Integer.parseInt(innerdata.get("category")));
        item.setQuantity(Integer.parseInt(innerdata.get("quantity")));
        item.setLastPurchasePrice(innerdata.get("price"));
        item.setRecyclerViewPosition(Integer.parseInt(innerdata.get("recyclerViewPosition")));
        return item;
    }
}
