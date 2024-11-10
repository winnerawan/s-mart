<?php
/*-------------------------------------------------------------------------
 * /
 *-------------------------------------------------------------------------
 */
$router->get('', [
	'as' => '',
	'uses' => 'IndexController@index',
	'middleware' => [
		'api.auth.unauthorizedToken'
	]
]);


// /*-------------------------------------------------------------------------
//  * /signup
//  *-------------------------------------------------------------------------
//  */
// $router->post('signup', [
// 	'as' => 'signup',
// 	'uses' => 'IndexController@signup',
// 	'middleware' => [
// 		'support.webThrottleRequests',
// 		'api.auth.unauthorizedToken',
// 		// 'support.trimStringsPost'
// 	]
// ]);

/*-------------------------------------------------------------------------
 * /signin
 *-------------------------------------------------------------------------
 */
$router->post('signin', [
	'as' => 'signin',
	'uses' => 'IndexController@signin',
	'middleware' => [
		'support.webThrottleRequests',
		'api.auth.unauthorizedToken',
		// 'support.trimStringsPost'
	]
]);

/*-------------------------------------------------------------------------
 * /signout
 *-------------------------------------------------------------------------
 */
$router->post('signout', [
	'as' => 'signout',
	'uses' => 'IndexController@signout',
	'middleware' => [
		'support.webThrottleRequests',
		'api.auth.authorizedToken'
	]
]);
