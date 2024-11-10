<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\PayType;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Delete extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'payType' => 'required|numeric'
    ];

    protected $payType;

     /**
	 */
	public function validate() {
		if (!parent::validate()) {
			return false;
		}
        $this->payType = Smart\PayType::select()
            ->where('pay_type.id', $this->validatorData->get('payType'))
            ->first();
        if (!$this->payType) {
            return false;
        }
        return true;
    }

    /**
     */
    public function delete() {
        return Smart\User::transaction(function(){
            // if ($this->payType->hasItems()) {
            //     return;
            // }
            // $this->payType->delete();
		});
    }

    

}