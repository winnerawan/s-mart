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
class Account extends Api\User\UserValidator
{
	/**
	 */
	protected $account;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		return true;
	}

	/**
	 */
	public function response(){
		$user =  $this->getUser();
        $response['user'] = $user;
		$response['user']['username'] = $user->getUsername()->username;
		$response['user']['identity'] = $user->getUsername()->identity_id;
		$response['user']['is_verified'] = $user->getUserStatus()->isStatusActive();
		$response['user']['is_security'] = $user->getUserType()->isTypeSecurity();
		$response['user']['is_system'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->isRoleSystem() : false;
		$response['user']['is_admin'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->isRoleAdmin() : false;
		$response['user']['is_second_admin'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->isRoleSecondAdmin() : false;
		$response['user']['is_secretary'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->isRoleSecretary() : false;
		$response['user']['is_accountant'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->isRoleAccountant() : false;
		$response['user']['role'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->name : null;
		$response['user']['image'] = $user->getUserPhoto()  != null ? sprintf('https:%s%s', Api\Config::get('media.file'), $user->getUserPhoto()->getMedia()->file) : null;
		$response['user']['home'] = $user->getUserHome() != null ? sprintf('%s %s', $user->getUserHome()->getHome()->getBlock()->name, $user->getUserHome()->getHome()->name) : null;
		$response['user']['home_id'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->id : null;
		$response['user']['block_id'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->block_id : null;
		$response['user']['home_name'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->name : null;
		$response['user']['block_name'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->getBlock()->name : null;
		$response['user']['relationship'] = $user->getFamilyRelationship() != null ? $user->getFamilyRelationship()->name : null;
		$response['user']['security_detail'] = $user->getUserType()->isTypeSecurity() ? $user->getSecurityDetail() : null;
		return $response;
	}
}