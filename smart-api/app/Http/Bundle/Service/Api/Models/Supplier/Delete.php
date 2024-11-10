<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Supplier;

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
        'supplier' => 'required|numeric'
    ];

    protected $supplier;

     /**
	 */
	public function validate() {
		if (!parent::validate()) {
			return false;
		}
        $this->supplier = Smart\Supplier::select()
            ->where('supplier.id', $this->validatorData->get('supplier'))
            ->first();
        if (!$this->supplier) {
            return false;
        }
        return true;
    }

    /**
     */
    public function delete() {
        return Smart\User::transaction(function(){
            if ($this->supplier->hasPurchases()) {
                return;
            }
            $this->supplier->delete();
		});
    }

    

}