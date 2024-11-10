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
class Update extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'supplier' => 'required|numeric',
        'name' => 'nullable|string',
        'address' => 'nullable|string',
        'phone' => 'nullable|string'
    ];

    protected $supplier;

     /**
	 */
	public function validate(){
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
    public function update() {
        return Smart\User::transaction(function(){
            $this->supplier->name = $this->validatorData->get('name');
            $this->supplier->address = $this->validatorData->get('address');
            $this->supplier->phone = $this->validatorData->get('phone');
            // $this->supplier->updated_user_id = $this->getUser()->id;
            // $this->supplier->is_updated = 1;
            $this->supplier->save();
		});
    }

    /**
     */
    public function updatePosition() {
        return Smart\User::transaction(function(){
            $this->supplier->position = $this->validatorData->get('position');
            // $this->supplier->updated_user_id = $this->getUser()->id;
            // $this->supplier->is_updated = 1;
            $this->supplier->save();
		});
    }
}