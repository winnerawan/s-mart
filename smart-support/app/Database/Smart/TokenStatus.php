<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class TokenStatus extends AbstractModel
{
	/**
	 */
	const STATUS_ACTIVE_ID = 1;
	const STATUS_REVOKED_ID = 2;
	
	/**
	 */
	protected $table = 'token_status';
	
	/**
	 */
	protected $visible = [
		'id',
		'name'
	];

	/**
	 */
	public function isStatusOf($id){
		return $this->id == $id;
	}

	/**
	 */
	public function isStatusActive(){
		return $this->isStatusOf(static::STATUS_ACTIVE_ID);
	}

	/**
	 */
	public function isStatusRevoke(){
		return $this->isStatusOf(static::STATUS_REVOKED_ID);
	}
}