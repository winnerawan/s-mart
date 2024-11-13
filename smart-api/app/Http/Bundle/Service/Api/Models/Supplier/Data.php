<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Supplier;

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
		$this->data = Smart\Supplier::select()
			->orderBy('name', 'ASC')
			// ->orderBy('created_datetime', 'DESC')
			->get();
		return true;
	}

	/**
	 */
	protected function wrapResponse($supplier) {
		$response = $supplier;
		if (str_contains($this->validatorData->get('with'), 'purchases')) {
			$response['purchases'] = $this->getPurchases($supplier->getPurchases());
		}
		// $response['item'] = $supplier->items()->count();
		return $response;
	}

	/**
	 */
	protected function wrapPurchase($purchase) {
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
	protected function getPurchases($purchases) {
		$response = [];
		foreach($purchases as $purchase) {
			$response[] = $this->wrapPurchase($purchase);
		}
		return $response;
	}
	/**
	 */
	protected function getData() {
		$response = [];
		foreach($this->data as $supplier) {
			$response[] = $this->wrapResponse($supplier);
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