<?php return[
	'api' => [
		/*
		 |--------------------------------------------------------------------------
		 | Auth
		 |--------------------------------------------------------------------------
		 */
		'auth' => [
			/*
			 |--------------------------------------------------------------------------
			 | Jwt
			 |--------------------------------------------------------------------------
			 */
			'jwt' => [
				'private_key' => storage_path('app/jwt/Auth-Bearer-Token(JWT-RSA-SHA256).key'),
				'private_key_passphrase' => 'H3+gWkJ_6uuVH#22V!^8^yJ^wU2TH99?UazKNgty5^85Z$a_vjMDcL27X9e=cc7h',
				'public_key' => storage_path('app/jwt/Auth-Bearer-Token(JWT-RSA-SHA256).key.pub'),
				'public_key_passphrase' => 'H3+gWkJ_6uuVH#22V!^8^yJ^wU2TH99?UazKNgty5^85Z$a_vjMDcL27X9e=cc7h'
			]
		],

		/*
		|--------------------------------------------------------------------------
		| ChatGPT
		|--------------------------------------------------------------------------
		*/
		'ai' => [
			'endpoint' => 'https://api.openai.com/v1/chat/completions',
			'key' => 'openai api key'
		],

		/*
		|--------------------------------------------------------------------------
		| OneSignal
		|--------------------------------------------------------------------------
		*/
		'onesignal' => [
			'enable' => true,
			'api_key' => 'onesignal api key',
			'app_id' => 'onesignal app id'
		],

		/*
		 |--------------------------------------------------------------------------
		 | Maps
		 |--------------------------------------------------------------------------
		 */
		'gmaps' => [
			'key' => 'gmaps key'
		],

		/*
		 |--------------------------------------------------------------------------
		 | Pdf Key
		 |--------------------------------------------------------------------------
		 */
		'pdf' => [
			'key' => '$2a$12$OKPJfQ.BqinT8nkCZghGvuk3FsN26sSK1ctQS.CbIpzJnjLqiqk02'
		],
		/*
		 |--------------------------------------------------------------------------
		 | Media
		 |--------------------------------------------------------------------------
		 */
		'media' => [
			'fit' => '//storage.s-mart.dev/images/',
			'file' => '//storage.s-mart.dev/images/',
			'storage' => '/app/smart-storage/media/'
		],
		/*
		 |--------------------------------------------------------------------------
		 | Doc
		 |--------------------------------------------------------------------------
		 */
		'document' => [
			'fit' => '//storage.s-mart.dev/document/',
			'file' => '//storage.s-mart.dev/document/',
			'storage' => '/app/smart-storage/document/'
		]
		
	]
];
