<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Purchase;

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
	protected $purchase;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->data = Smart\Purchase::select('purchase.*')
			// ->join('purchase_item', 'purchase_item.purchase_id', 'purchase.id')
			->orderBy('created_at', 'ASC');
			// ->orderBy('created_datetime', 'DESC')
		
		$this->purchase = $this->validatorData->get('purchase');
		if ($this->purchase!=null) {
			$this->data = $this->data->where([
				'purchase.id' => $this->purchase
			])->first();
			return true;
		}
		$this->data = $this->data->get();
		return true;
	}

	/**
	 */
	protected function wrapResponse($purchase) {
		$response = $purchase;
		$response['supplier_name'] = $purchase->getSupplier()->name;
		$response['item'] = $purchase->items()->count();
		if (str_contains($this->validatorData->get('with'), 'pieces')) {
			$response['pieces'] = (int) $purchase->pieces();
		}
		if (str_contains($this->validatorData->get('with'), 'items')) {
			$response['items'] = $purchase->getItems();
		}
		
		return $response;
	}
	/**
	 */
	protected function getData() {
		$response = [];
		if ($this->purchase!=null) {
			return $this->wrapResponse($this->data);
		}
		foreach($this->data as $purchase) {
			$response[] = $this->wrapResponse($purchase);
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