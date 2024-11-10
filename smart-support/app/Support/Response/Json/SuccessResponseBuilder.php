<?php
/**
 */
namespace App\Support\Response\Json;

use App\Support\Response\JsonResponse;

/**
 */
class SuccessResponseBuilder extends ResponseBuilder
{
	/**
	 */
	protected $status;
	
	/**
	 */
	public function __construct($status = null){
		if ($status !== null) {
			$this->status($status);
		}
	}
	
	/**
	 */
	public function status($status){
		if (!$status instanceof Model\Status) {
			$status = new Model\Status($status);
		}
		$this->status = $status;
		return $this;
	}

	/**
	 */
	public function getStatus(){
		if ($this->status === null) {
			$this->status(JsonResponse::HTTP_OK);
		}
		return $this->status;
	}

	/**
	 */
	public function getData(){
		$data[static::KEY_STATUS] = $this->getStatus();
		return $data;
	}
}