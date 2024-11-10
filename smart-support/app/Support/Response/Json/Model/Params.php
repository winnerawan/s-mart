<?php
/**
 */
namespace App\Support\Response\Json\Model;

use JsonSerializable;
use Illuminate\Contracts\Support\Arrayable;

/**
 */
class Params implements JsonSerializable
{
	/**
	 */
	protected $data;
	protected $name;
	protected $message;

	/**
	 */
	public function __construct($data = null){
		$this->data = $data instanceof Arrayable ? $data->toArray() : $data;
	}

	/**
	 */
	public function getName(){
		if ($this->name !== null) {
			return $this->name;
		}
		$name = '';
		if ($this->data != null) {
			$name = key($this->data);
		}
		return $this->name = $name;
	}

	/**
	 */
	public function getMessage(){
		if ($this->message !== null) {
			return $this->message;
		}
		$message = '';
		if ($this->data != null && $messages = reset($this->data)) {
			$message = reset($messages);
		}
		return $this->message = $message;
	}

	/**
	 */
	public function jsonSerialize(){
		return $this->data;
	}
}