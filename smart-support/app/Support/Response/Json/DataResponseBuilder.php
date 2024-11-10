<?php
/**
 */
namespace App\Support\Response\Json;

/**
 */
class DataResponseBuilder extends SuccessResponseBuilder
{
	/**
	 */
	protected $data;
	
	/**
	 */
	public function __construct($data = null){
		if ($data !== null) {
			$this->data($data);
		}
	}
	
	/**
	 */
	public function data($data){
		$this->data = $data;
		return $this;
	}

	/**
	 */
	public function getData(){
		return $this->data;
	}
}