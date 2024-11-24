<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Purchase;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Delete extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'purchase' => 'required|string'
    ];

    protected $purchase;

     /**
	 */
	public function validate() {
		if (!parent::validate()) {
			return false;
		}
        $this->purchase = Smart\Purchase::select()
            ->where('purchase.id', $this->validatorData->get('purchase'))
            ->first();
        if (!$this->purchase) {
            return false;
        }
        return true;
    }

    /**
     */
    public function delete() {
        return Smart\User::transaction(function(){
           $this->revertItemStocks();
           $this->purchase->delete();
		});
    }
    
    /**
     */
    protected function revertItemStocks() {
        foreach($this->purchase->getItems() as $item) {
            $itemStock = $this->getItemStock($item->category_id, $item->sku, $item->item_id);
            if ($itemStock) {
                $itemStock->stock = $itemStock->stock - $item->qty;
                $itemStock->save();
                $item->delete();
            }
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

}