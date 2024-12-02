<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Sale;

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
        // 'customer' => 'required|numeric',
        // 'items.*' => 'required|array'
    ];

    protected $customer;
    protected $sale;
    protected $total;
    protected $totalPurchasePrice;
    protected $totalProfit;
     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        // $this->customer = Smart\Customer::select()
        //     ->where('customer.id', $this->validatorData->get('customer'))
        //     ->first();
        // if (!$this->customer) {
        //     return false;
        // }
        return true;
    }

    /**
     */
    public function create() {
        return Smart\User::transaction(function(){
            $now = \Carbon\Carbon::now();
            $this->sale = new Smart\Sale();
            $json = json_decode($this->request->getContent(), true);
            $this->sale->customer_id = $json['customer'];
            $this->sale->description = $json['description'];
            $this->sale->save();
            $this->createSaleItems($json, $this->sale);
            // $this->sale->total = $this->total;
            // $this->sale->save();
        
            $this->updateStocks($json);
            // $this->updateLastSalePriceItems($json);
		});
    }

    /**
     */
    protected function createSaleItems($json, $sale) {
        foreach($json['items'] as $item) {
            $saleItem = new Smart\SaleItem();
            $saleItem->sale_id = $sale->id;
            $saleItem->item_id = $item['item'];
            $saleItem->price = $item['selling_price'];
            $saleItem->qty = $item['qty'];
            $saleItem->sku = $item['sku'];
            $saleItem->category_id = $item['category'];
            $saleItem->subtotal = ( $item['selling_price'] * $item['qty'] );
            $saleItem->purchase_price = $item['purchase_price'];
            $saleItem->profit =  ($saleItem->price * $saleItem->qty) - ($saleItem->purchase_price * $saleItem->qty);                      //$saleItem->subtotal - ( $item['purchase_price'] * $item['qty'] );

            $this->total += $saleItem->subtotal;
            $this->totalPurchasePrice += ( $item['purchase_price'] * $item['qty'] );
            $this->totalProfit += $saleItem->profit;

            $saleItem->save();

        }
        $sale->total = $this->total;
        $sale->subtotal += $this->total;//
        $sale->discount = 0;
        $sale->total_purchase_price = $this->totalPurchasePrice;
        $sale->total_profit = $this->totalProfit;
        $sale->save();
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

    // /**
    //  */
    // protected function updateLastSalePriceItems($json) {
    //     foreach($json['items'] as $item) {
    //         $mItem = $this->getItem($item['category'], $item['sku'], $item['item']);
    //         if ($mItem!=null) {
    //             if ($mItem->last_purchase_price==null) {
    //                 $mItem->last_purchase_price = $item['selling_price'];
    //                 $mItem->save();
    //             } 
    //         }
    //     }
    // }
    
    /**
     */
    protected function updateStocks($json) {
        foreach($json['items'] as $item) {
            $itemStock = $this->getItemStock($item['category'], $item['sku'], $item['item']);
            if ($itemStock!=null) {
                $this->updateStock($item, $itemStock);
            }
        }
    }

    /**
     */
    protected function updateStock($item, $itemStock) {
        $itemStock->item_id = $item['item'];
        $itemStock->sku = $item['sku'];
        $itemStock->category_id = $item['category'];
        $itemStock->stock = $itemStock->stock - $item['qty'];
        $itemStock->updated_at = \Carbon\Carbon::now();
        $itemStock->save();
    }
}