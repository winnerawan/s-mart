<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Stock;

use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Data extends Api\User\UserValidator
{
	/**
	 */
	protected $data;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->data = Smart\ItemStock::select()
			->join('item', function($join) {
				$join->on('item.category_id', 'item_stock.category_id');
				$join->on('item.sku', 'item_stock.sku');
				$join->on('item.id', 'item_stock.item_id');
			})
			// ->orderBy('name', 'ASC')
			// ->orderBy('created_datetime', 'DESC')
			->get();
		return true;
	}

	/**
	 */
	protected function wrapResponse($category) {
		$response = $category;
		if ($this->validatorData->get('with')=='items') {
			$response['item'] = $category->items()->count();
		}
		return $response;
	}
	/**
	 */
	protected function getData() {
		$response = [];
		foreach($this->data as $category) {
			$response[] = $this->wrapResponse($category);
		}
		return $response;
	}

	/**
	 */
	public function response(){
        $response = $this->getData();
		return $response;
	}
}