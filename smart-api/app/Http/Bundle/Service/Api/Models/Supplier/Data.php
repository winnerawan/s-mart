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
		// $response['item'] = $supplier->items()->count();
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
        $response[] = $this->data;
		return $response;
	}
}