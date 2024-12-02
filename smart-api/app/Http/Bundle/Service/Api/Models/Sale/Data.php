<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Sale;

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
	protected $sale;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->data = Smart\Sale::select('sale.*')
			// ->join('sale_item', 'sale_item.sale_id', 'sale.id')
			->orderBy('created_at', 'ASC');
			// ->orderBy('created_datetime', 'DESC')
		
		$this->sale = $this->validatorData->get('sale');
		if ($this->sale!=null) {
			$this->data = $this->data->where([
				'sale.id' => $this->sale
			])->first();
			return true;
		}
		$this->data = $this->data->get();
		return true;
	}

	/**
	 */
	protected function wrapResponse($sale) {
		$response = $sale;
		$response['customer_name'] = $sale->getCustomer()->name;
		$response['item'] = $sale->items()->count();
		if (str_contains($this->validatorData->get('with'), 'pieces')) {
			$response['pieces'] = (int) $sale->pieces();
		}
		if (str_contains($this->validatorData->get('with'), 'items')) {
			$response['items'] = $sale->getItems();
		}
		
		return $response;
	}
	/**
	 */
	protected function getData() {
		$response = [];
		if ($this->sale!=null) {
			return $this->wrapResponse($this->data);
		}
		foreach($this->data as $sale) {
			$response[] = $this->wrapResponse($sale);
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