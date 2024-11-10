<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class BranchStatus extends AbstractModel
{
	/**
	 */
	const STATUS_ACTIVE_ID = 1;
	const STATUS_REMOVED_ID = 2;
	
	/**
	 */
	protected $table = 'branch_status';

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
	public function isStatusRemoved(){
		return $this->isStatusOf(static::STATUS_REMOVED_ID);
	}
}