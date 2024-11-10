<?php
/**
 */
namespace App\Support\Response\Json;

use App\Support\Response\JsonResponse;

/**
 */
class ErrorResponseBuilder extends ResponseBuilder
{
	/**
	 */
	protected $code;
	protected $message;
	protected $params;
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
	public function code($code){
		$this->code = $code;
		return $this;
	}
	
	/**
	 */
	public function getCode(){
		return $this->code;
	}

	/**
	 */
	public function message($message){
		$this->message = $message;
		return $this;
	}

	/**
	 */
	public function getMessage(){
		if ($this->message == null) {
			$this->message = $this->getParams()->getMessage();
			if ($this->message == null) {
				$this->message = $this->getStatus()->getMessage();
			}
		}
		return $this->message;
	}
	
	/**
	 */
	public function params($params){
		if (!$params instanceof Model\Params) {
			$params = new Model\Params($params);
		}
		$this->params = $params;
		return $this;
	}

	/**
	 */
	public function getParams(){
		if ($this->params === null) {
			$this->params(new Model\Params());
		}
		return $this->params;
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
			$this->status(JsonResponse::HTTP_BAD_REQUEST);
		}
		return $this->status;
	}

	/**
	 */
	public function getError(){
		return new Model\Error($this->getMessage(), $this->getCode());
	}

	/**
	 */
	public function getParam(){
		return new Model\Param($this->getParams()->getMessage(), $this->getParams()->getName());
	}
	
	/**
	 */
	public function getData(){
		$data[static::KEY_ERROR] = $this->getError();
		$data[static::KEY_STATUS] = $this->getStatus();
		$data[static::KEY_PARAM] = $this->getParam();
		$data[static::KEY_PARAMS] = $this->getParams();
		return $data;
	}
}