<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Purchase;

use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Str;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Create extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        // 'supplier' => 'required|numeric',
        // 'items.*' => 'required|array'
    ];

    protected $supplier;
    protected $purchase;
    protected $total;
     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        // $this->supplier = Smart\Supplier::select()
        //     ->where('supplier.id', $this->validatorData->get('supplier'))
        //     ->first();
        // if (!$this->supplier) {
        //     return false;
        // }
        return true;
    }

    /**
     */
    public function create() {
        return Smart\User::transaction(function(){
            $now = \Carbon\Carbon::now();
            $this->purchase = new Smart\Purchase();
            // $this->purchase->id = Str::uuid();
            $json = json_decode($this->request->getContent(), true);
            $this->purchase->supplier_id = $json['supplier'];
            $this->purchase->description = $json['description'];

            $this->createPurchaseItems($json, $this->purchase);
            $this->purchase->total = $this->total;
            $this->purchase->save();
        
            $this->createOrUpdateStocks($json);
            $this->updateLastPurchasePriceItems($json);
		});
    }

    /**
     */
    protected function createPurchaseItems($json, $purchase) {
        foreach($json['items'] as $item) {
            $purchaseItem = new Smart\PurchaseItem();
            $purchaseItem->purchase_id = $purchase->id;
            $purchaseItem->item_id = $item['item'];
            $purchaseItem->price = $item['purchase_price'];
            $purchaseItem->qty = $item['qty'];
            $purchaseItem->subtotal = ( $item['purchase_price'] * $item['qty'] );

            $this->total += $purchaseItem->subtotal;

            $purchaseItem->save();

        }
    }

    /**
     */
    protected function getItemStock($category, $sku, $item) {
        return Smart\ItemStock::where([
            'item_stock.category_id' => $category,
            'item_stock.sku' => $sku, 
            'item_stock.item_id' => $item
        ])->first();
    }


    /**
     */
    protected function getItem($category, $sku, $item) {
        return Smart\Item::where([
            'item.category_id' => $category,
            'item.sku' => $sku, 
            'item.id' => $item
        ])->first();
    }

    /**
     */
    protected function updateLastPurchasePriceItems($json) {
        foreach($json['items'] as $item) {
            $mItem = $this->getItem($item['category'], $item['sku'], $item['item']);
            if ($mItem->last_purchase_price==null) {
                $mItem->last_purchase_price = $item['purchase_price'];
                $mItem->save();
            } 
        }
    }
    
    /**
     */
    protected function createOrUpdateStocks($json) {
        foreach($json['items'] as $item) {
            $itemStock = $this->getItemStock($item['category'], $item['sku'], $item['item']);
            if ($itemStock==null) {
                $this->createStock($item);
            } else {
                $this->updateStock($item, $itemStock);
            }
        }
    }

    /**
     */
    protected function createStock($item) {
        $itemStock = new Smart\ItemStock();
        $itemStock->item_id = $item['item'];
        $itemStock->sku = $item['sku'];
        $itemStock->category_id = $item['category'];
        $itemStock->stock = $item['qty'];
        $itemStock->purchase_price = $item['purchase_price'];
        $itemStock->selling_price = $item['selling_price'];
        $itemStock->save();
    }

    /**
     */
    protected function updateStock($item, $itemStock) {
        $itemStock->item_id = $item['item'];
        $itemStock->sku = $item['sku'];
        $itemStock->category_id = $item['category'];
        $itemStock->stock = $itemStock->stock + $item['qty'];
        $itemStock->purchase_price = $item['purchase_price'];
        $itemStock->selling_price = $item['selling_price'];
        $itemStock->updated_at = \Carbon\Carbon::now();
        $itemStock->save();
    }
}