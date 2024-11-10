<?php
/**
 */
namespace App\Support\Middleware;

use Illuminate\Foundation\Http\Middleware\TransformsRequest;

/**
 */
class TrimStrings extends TransformsRequest
{
	/**
	 */
	protected $except = [];
	
	/**
	 */
	protected function clean($request){
		$this->cleanParameterBag($request->query);
		$this->cleanParameterBag($request->request);
	}

	/**
	 */
	protected function cleanArray(array $data, $keyPrefix = ''){
		$result = [];
		foreach ($data as $key => $value) {
			$result[$key] = $this->cleanValue($key, $value);
		}
		return $result;
	}

	/**
	 */
	protected function transform($key, $value){
		return in_array($key, $this->except, true) ? $value : (is_string($value) ? trim($value) : $value);
	}
}