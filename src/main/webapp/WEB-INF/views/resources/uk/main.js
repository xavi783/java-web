require.config({
    //baseUrl: '/web/', 
    baseUrl: '../../resources/',
    paths: {
    	'jquery': 'https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min',
        'stripe': 'https://checkout.stripe.com/checkout',
        'jqueryui': 'jui/jui.min',
        'primeui': 'pui/pui-1.0',
        'uk-jquerypui': 'uk/uk-jquerypui',
        'underscore': 'bb/underscore-min',
        'backbone': 'bb/backbone-min',
        'uk-backbone': 'uk/uk-backbone',
        'uk-shop': 'uk/uk-shop',
        'ck-editor': 'cke/ckeditor'
    },
    shim: {
        'jquery': {
            exports: '$'
        },
        'jqueryui': {
            deps: ['jquery']
        },
        'primeui': {
            deps: ['jqueryui']
        },
        'uk-jquerypui': {
            deps: ['primeui']
        },
        'underscore': {
            exports: '_'
        },
        'backbone': {
            deps: ['underscore', 'primeui'],
            exports: 'Backbone'
        },
        'uk-backbone': {
            deps: ['stripe','backbone','uk-jquerypui']
        },
        'uk-shop': {
            deps: ['uk-backbone','ck-editor']
        },
    }
});

require(['uk-shop']); //accediendo al ultimo punto