<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Customer;

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
		$this->data = Smart\Customer::select()
			->orderBy('name', 'ASC')
			// ->orderBy('created_datetime', 'DESC')
			->get();
		return true;
	}

	/**
	 */
	protected function wrapResponse($customer) {
		$response = $customer;
		if ($this->validatorData->get('with')=='sales') {
			$response['sales'] = $customer->items()->count();
		}
		return $response;
	}
	/**
	 */
	protected function getData() {
		$response = [];
		foreach($this->data as $customer) {
			$response[] = $this->wrapResponse($customer);
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