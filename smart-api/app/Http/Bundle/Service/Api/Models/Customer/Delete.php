<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Customer;

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
        'customer' => 'required|numeric'
    ];

    protected $customer;

     /**
	 */
	public function validate() {
		if (!parent::validate()) {
			return false;
		}
        $this->customer = Smart\Customer::select()
            ->where('customer.id', $this->validatorData->get('customer'))
            ->first();
        if (!$this->customer) {
            return false;
        }
        return true;
    }

    /**
     */
    public function delete() {
        return Smart\User::transaction(function(){
            if ($this->customer->hasSales()) {
                return;
            }
            $this->customer->delete();
		});
    }

    

}