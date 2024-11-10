<?php
/**
 */
namespace App\Support\Validator;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

/**
 */
abstract class AbstractValidator
{
	/**
	 */
	protected $request;
	protected $validator;
	protected $validatorData;
	protected $validatorRules = [];
	protected $validatorMessages = [];

	/**
	 */
	public function __construct(Request $request){
		$this->request = $request;
	}

	/**
 	 */
	public function getRequest(){
		return $this->request;
	}

	/**
 	 */
	public function getValidator(){
		if ($this->validator !== null) {
			return $this->validator;
		}
		$this->validator = Validator::make($this->getValidatorData()->all(), $this->getValidatorRules(), $this->getValidatorMessages());
		return $this->validator;
	}

	/**
 	 */
	public function getValidatorData(){
		if ($this->validatorData !== null) {
			return $this->validatorData;
		}
		if ($this->request->getMethod() == Request::METHOD_GET) {
			$this->validatorData = $this->request->query;
			return $this->validatorData;
		}
		$this->validatorData = $this->request->request;
		$this->validatorData->add($this->request->allFiles());
		return $this->validatorData;
	}

	/**
 	 */
	public function getValidatorRules(){
		return $this->validatorRules;
	}

	/**
 	 */
	public function getValidatorMessages(){
		return $this->validatorMessages;
	}

	/**
 	 */
	public function hasRequestValue($name){
		return $this->validatorData->has($name);
	}

	/**
 	 */
	public function getRequestValue($name, $default = ''){
		return $this->validatorData->get($name, $default);
	}
	
	/**
 	 */
	public function hasValidatorError($name){
		return $this->validator->errors()->has($name);
	}

	/**
 	 */
	public function getValidatorError($name, $default = ''){
		if ($this->hasValidatorError($name)) {
			return $this->validator->errors()->first($name);
		}
		return $default;
	}
	
	/**
	 */
	public function validate(){
		return !$this->getValidator()->fails();
	}
}