<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Item;

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
        'item' => 'required|string'
    ];

    protected $item;

     /**
	 */
	public function validate() {
		if (!parent::validate()) {
			return false;
		}
        $this->item = Smart\Item::select()
            ->where('item.id', $this->validatorData->get('item'))
            ->first();
        if (!$this->item) {
            return false;
        }
        return true;
    }

    /**
     */
    public function delete() {
        return Smart\User::transaction(function(){
            if ($this->item->hasStocks()) {
                return;
            }
            $this->item->delete();
		});
    }

    

}