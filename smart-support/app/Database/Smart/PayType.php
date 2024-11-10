<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class PayType extends AbstractModel
{
	/**
	 */
	protected $table = 'pay_type';
	
	/**
	 */
	protected $visible = [
		'id',
		'name'
	];
	
	/**
	 */
	public function isTypeOf($id){
		return $this->id == $id;
	}


}