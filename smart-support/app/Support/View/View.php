<?php
/**
 */
namespace App\Support\View;

use Illuminate\Support\Facades;

/**
 */
abstract class View
{
	/**
	 */
	protected $name;
	protected $view;
	protected $params;
	protected $prefix;
	
	/**
	 */
	public function __construct($name, array $params = null){
		$this->name = $name;
		$this->params = (array)$params;
	}

	/**
	 */
	public function getName(){
		return $this->name;
	}

	/**
	 */
	public function getView(){
		if ($this->view === null) {
			$this->view = $this->prefix . $this->name;
		}
		return $this->view;
	}

	/**
	 */
	public function param($key, $value){
		$this->params[$key] = $value;
		return $this;
	}

	/**
	 */
	public function params(array $params){
		foreach($params as $key => $value){
			$this->param($key, $value);
		}
		return $this;
	}

	/**
	 */
	public function getParam($key, $value = null){
		return array_key_exists($key, $this->params) ? $this->params[$key] : $value;
	}
	
	/**
	 */
	public function getParams(){
		return $this->params;
	}

	/**
	 */
	public function render(){
		return Facades\View::make($this->getView())->with('__page', $this);
	}
}