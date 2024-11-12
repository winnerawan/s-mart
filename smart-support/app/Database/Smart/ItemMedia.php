<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class ItemMedia extends AbstractModel
{
    /**
     */
    protected $table = 'item_media';

    /**
     */
    protected $primaryKeys = [
        'item_id' => 'item_id',
        'sku' => 'sku',
        'category_id' => 'category_id',
        'media_uid' => 'media_uid'
    ];

    /**
     */
    protected $media;

    /**
     */
    public function media(){
        return Media::where([
            'media.uid' => $this->media_uid
        ]);
    }

    /**
     */
    public function getMedia(){
        if ($this->media==null) {
            $this->media = $this->media()
                // ->orderBy('created_datetime', 'DESC')
                ->first();
        }
        return $this->media;
    }
}