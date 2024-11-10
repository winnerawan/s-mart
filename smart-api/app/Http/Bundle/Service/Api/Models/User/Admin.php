<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\User;

use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Admin extends Api\User\UserValidator
{
	/**
	 */
	protected $data;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->data = Smart\User::select('user.*')
			->join('user_role', 'user_role.user_id', 'user.id')
			->join('role', 'role.id', 'user_role.role_id')
			->where('user_status_id', Smart\UserStatus::STATUS_ACTIVE_ID)
			// ->where('user.id', '!=', $this->getUser()->id)
			->where('user_role.role_id', '!=', Smart\Role::ROLE_ADMIN)
			->where('user_role.period_id', $this->getPeriod()->id)
			->orderBy('role.position', 'ASC');

		$excludeId = $this->validatorData->get('exclude');
		if ($excludeId!=null) {
			$this->data = $this->data->where('id', '!=', $excludeId);
		}
		$this->data = $this->data->get();
		return true;
	}

	/**
	 */
	protected function getPeriod() {
		$year = \Carbon\Carbon::now()->format('Y');
		return Smart\Period::select()
			->where('name', $year)
			->first();
	}

	/**
	 */
	protected function wrapUserResponse($user) {
		$response = $user;
		$response['role'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->name : null;
		$response['is_verified'] = $user->getUserStatus()->isStatusActive();
		$response['is_admin'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->isRoleAdmin() : false;
		$response['datetime'] = Support\Format::formatDateTime($user->updated_datetime != null ? $user->updated_datetime : $user->created_datetime);
		$response['image'] = $user->getUserPhoto()  != null ? sprintf('https:%s%s', Api\Config::get('media.file'), $user->getUserPhoto()->getMedia()->file) : null;
		$response['home'] = $user->getUserHome() != null ? sprintf('%s %s', $user->getUserHome()->getHome()->getBlock()->name, $user->getUserHome()->getHome()->name) : null;
		$response['relationship'] = $user->getFamilyRelationship() != null ? $user->getFamilyRelationship()->name : null;
		$response['home_id'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->id : null;
		$response['block_id'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->block_id : null;
		$response['home_name'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->name : null;
		$response['is_security'] = $user->getUserType()->isTypeSecurity();
		$response['block_name'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->getBlock()->name : null;
		return $response;
	}

	/**
	 */
	protected function getData() {
		$response = [];
		foreach($this->data as $admin) {
			$response[] = $this->wrapUserResponse($admin);
		}
		return $response;
	}

	/**
	 */
	public function response(){
		$response['period'] = $this->getPeriod();
        $response['data'] = $this->getData();
		return $response;
	}
}