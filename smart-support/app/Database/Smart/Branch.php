<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Branch extends AbstractModel
{
	/**
	 */
	protected $table = 'branch';

	/**
	 */
	protected $visible = [
		'id',
		'name'
	];

	/**
	 */
	protected $branchStatus;
	protected $branchTimezone;
	protected $userBranchs;


	/**
	 */
	public function branchStatus(){
		return BranchStatus::where([
			'branch_status.id' => $this->branch_status_id
		]);
	}
	
	/**
	 */
	public function getBranchStatus(){
		if ($this->branchStatus == null) {
			$this->branchStatus = $this->branchStatus()->first();
		}
		return $this->branchStatus;
	}
	
	/**
	 */
	public function branchTimezone(){
		return BranchTimezone::where([
			'branch_timezone.branch_id' => $this->id
		]);
	}
	
	/**
	 */
	public function getBranchTimezone(){
		if ($this->branchTimezone == null) {
			$this->branchTimezone = $this->branchTimezone()->first();
		}
		return $this->branchTimezone;
	}

	/**
	 */
	public function userBranchs(){
		return UserBranch::where([
			'user_branch.branch_id' => $this->id
		]);
	}
	
	/**
	 */
	public function getUserBranchs(){
		if ($this->userBranchs == null) {
			$this->userBranchs = $this->userBranchs()->get();
		}
		return $this->userBranchs;
	}
}