
$.uk =  $.uk || {};
$.tiny = $.tiny || {};

$.fn.extend({
    serializeObject: function(){
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    },
    askFor: function(uri,callback) {
        $.ajax({
            url: uri,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json'
        }).success(callback);
    },
    giveTo: function(data,url,callback){
        $.ajax({
            url: url,
            type: 'POST',
            data: data,
            dataType: 'json',
            contentType: 'application/json'
        }).success(callback);
    },
    postJSON: function(uri,data,callbacks) {
        if(callbacks==undefined) callbacks={};
        $.ajax({
            url: uri,
            type: 'POST',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json'
        })
        .done(callbacks.success||callbacks)
        .error(callbacks.error);
    },
    postForm: function(element,url,callback) {
        var data = $(element).serializeObject();
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json'
        }).success(callback);
    },
    jsonCallback: function(success,id,mssg){
        return function(r){
            if(r.logged==true){
                success(r);
            }else{
                if ($(id).length>0){$(id).html(mssg);}
            }
        };
    },
    exist: function(){
        return (this.length > 0);
    },
    path: function(route){
        return window.location.pathname+route;
    }
});

//Cloud-Tag
;(function($){
    function tag_limits(){
        this.sup = 1.5;
        this.inf = 0.6;
        this.range = (this.sup - this.inf);
        return {
            sup: this.sup,
            inf: this.inf,
            range: this.range
        };
    };
    $.fn.tagWeight = function(lim){
        $('.cloud-tag .tag').each(function(){
            var e = $(this);
            var per = parseInt(e.attr('data-size'))/100;
            e.css("font-size", (lim.inf+lim.range*per)+"em");
        });
    };
    //pesamos .tag en el documento segun los limites de em de tag_limits y .tag[data-size]
    $('document').ready(function(){ $.fn.tagWeight(tag_limits()); });
}(jQuery));

//Form validation:
;(function($){
    // Se aplica a todos los formularios de una pagina, solo cuando estan toda la pagina cargada
    // clase a darle al input cuando tiene contenido y no es valido (es el nombre de la clase CSS)
    var claseError = "invalid";
    var claseValid = "valid";
    onChange = function(){
        e = this;
        if(!e.validity.valid && e.value!=""){
            e.classList.add(claseError);
        };
        if(e.validity.valid && e.value!=""){
            e.classList.add(claseValid);
        };
        if((!e.validity.valid  || e.value=="") && e.classList.contains(claseValid)){
            e.classList.remove(claseValid);
        };
        if((e.validity.valid  || e.value=="") && e.classList.contains(claseError)){
            e.classList.remove(claseError);
        };
    };

    document.onreadystatechange = function () {
        if (document.readyState == "interactive") {
            for(var i=0; i<document.forms.length; i++){
                $(document.forms[i].elements).each(function(){
                    this.onchange = onChange;
                });
            };
        };
    };
}(jQuery));

//Sliders:
;(function($){
    $(function(){
        $(document.getElementsByClassName('slider')).each(function(){
            var slider        = $(this);
            var container     = slider.find('.slides-container');
            var slides        = slider.find('.slide');
            var running       = 0;
            var interval      = null;
            var timeStep      = parseInt($(this).attr("data-delay"),10) || 1000;
            var slideCount    = slider.find('.slide').length;
            var slideWidth    = slider.width();
            var slideHeight   = slider.height();
            var sliderUlWidth = slideCount * slideWidth;

            slider.css({ width: slideWidth, height: slideHeight }); 
            slides.css({ width: slideWidth, height: slideHeight });
            container.css({ width: sliderUlWidth, marginLeft: -slideWidth }); 
            slider.find('.slide:last-child').prependTo(container);

            function moveLeft() {
                container.animate({left: +slideWidth}, 200, function () {
                    slider.find('.slide:last-child').prependTo(container);
                    container.css('left', '');
                });
            };

            function moveRight() {
                container.animate({ left: -slideWidth }, 200, function () {
                    slider.find('.slide:first-child').appendTo(container);
                    container.css('left', '');
                });
            };

            slider.find('.ctrl_prev').click(function(){
                moveLeft();
            });

            slider.find('.ctrl_next').click(function () {
                moveRight();
            });

            slider.find('.ctrl_play').click(function(){
                if(running===0){
                    interval = setInterval(function(){ moveRight(); }, timeStep);
                    slider.find('.ctrl_play')[0].innerHTML = "&#xe037;";
                    running = 1;
                } else if(running===1) {
                    clearInterval(interval);
                    slider.find('.ctrl_play')[0].innerHTML = "&#xe036;";
                    running = 0;
                }
            });

        });
    });

    $(window).resize(function() {
        $(document.getElementsByClassName('slider')).each(function(){
            var slider        = $(this);
            var container     = slider.find('.slides-container');
            var slides        = slider.find('.slide');
            var slideCount    = slider.find('.slide').length;

            var slideWidth    = slider.parent().width();
            var slideHeight   = slider.parent().height();
            var sliderUlWidth = slideCount * slideWidth;

            slider.css({ width: slideWidth, height: slideHeight }); 
            slides.css({ width: slideWidth, height: slideHeight });
            container.css({ width: sliderUlWidth, marginLeft: -slideWidth });
        });
    });

    $(document).ready(function(){
        $(document.getElementsByClassName('slider')).find('.ctrl_play').click();
    });
}(jQuery));

;(function($){
    $.uk.post = {
        options: {
            total_stars  : 5,               //#maximo de notas a dar    
            icon_fully   : "&#xe042;",      // codigo de icono de nota = 1
            icon_fifty   : "&#xe041;",      // codigo de icono de nota = 0.5
            icon_emmpty  : "&#xe040;",      // codigo de icono de nota = 0
            stars_class  : ".stars",        // ID del elemento dentro de la que se incluiran las notas
            stars_attr   : "data-mark",     // Nombre del atributo que contendra el valor numerico de la nota
            full_class   : "full",          // Clase para el icono de nota = 1
            fifty_class  : "full",          // Clase para el icono de nota = 0.5
            empty_class  : "empty"          // Clase para el icono de nota = 0
        }
    };
    $.fn.blogpost = function( params ){
        var options = $.extend( {}, $.uk.post.options, params );        
        this.each(function(){ 
            $(this).data('ukblogpost', new Blogpost($(this),options) );  // almacena un objeto blogspot en el nodo en cuestion
        });
        return this;
    };
    function Blogpost(root,options){
        var oSelf = this,
            post  = $(this),
            stars = post.find(options.stars_class),
            mark  = stars.attr(options.stars_attr),
            fully = Math.floor(mark),
            fifty = (Math.floor(mark)!=Math.round(mark))?1:0,
            empty = options.total_stars-(fully+fifty);

        function initialize(){
            return oSelf;
        }

        this.qualify = function(){
            var innerHTML = "";
            for (var i=0; i<fully; i++) {
                innerHTML+=("<span class=\"full\">"+ICON_FULLY+"<\span>");
            };
            for (var i=0; i<fifty; i++) {
                innerHTML+=("<span class=\"full\">"+ICON_FIFTY+"<\span>");
            };
            for (var i=0; i<empty; i++) {
                innerHTML+=("<span class=\"empty\">"+ICON_EMPTY+"<\span>");
            };
            stars.html(innerHTML);
        }

        return initialize();
    };

    //Scroll-Area        
    $.tiny.scrollbar = {
        options: {
             axis         : 'y'    // vertical or horizontal scrollbar? ( x || y ).
            ,wheel        : 40     // how many pixels must the mouswheel scroll at a time.
            ,scroll       : true   // enable or disable the mousewheel.
            ,lockscroll   : true   // return scrollwheel to browser if there is no more content.
            ,size         : 'auto' // set the size of the scrollbar to auto or a fixed number.
            ,sizethumb    : 'auto' // set the size of the thumb to auto or a fixed number.
            ,invertscroll : false  // Enable mobile invert style scrolling
        }
    };
    $.fn.tinyscrollbar = function( params ){
        var options = $.extend( {}, $.tiny.scrollbar.options, params );        
        this.each(function(){ 
            $(this).data('tsb', new Scrollbar($(this),options) ); 
        });
        return this;
    };
    $.fn.tinyscrollbar_update = function(sScroll)    {
        return $(this).data('tsb').update(sScroll); 
    };
    function Scrollbar( root, options ){
        var oSelf    = this
        ,oWrapper    = root
        ,oViewport   = { obj: $( '.viewport', root ) }
        ,oContent    = { obj: $( '.overview', root ) }
        ,oScrollbar  = { obj: $( '.scrollbar', root ) }
        ,oTrack      = { obj: $( '.track', oScrollbar.obj ) }
        ,oThumb      = { obj: $( '.thumb', oScrollbar.obj ) }
        ,sAxis       = options.axis === 'x'
        ,sDirection  = sAxis ? 'left' : 'top'
        ,sSize       = sAxis ? 'Width' : 'Height'
        ,iScroll     = 0
        ,iPosition   = { start: 0, now: 0 }
        ,iMouse      = {}
        ,touchEvents = 'ontouchstart' in document.documentElement;

        function initialize(){
            oSelf.update();
            setEvents();
            return oSelf;
        }

        this.update = function( sScroll ){
            oViewport[ options.axis ] = oViewport.obj[0][ 'offset'+ sSize ];
            oContent[ options.axis ]  = oContent.obj[0][ 'scroll'+ sSize ];
            oContent.ratio            = oViewport[ options.axis ] / oContent[ options.axis ];

            oScrollbar.obj.toggleClass( 'disable', oContent.ratio >= 1 );

            oTrack[ options.axis ] = options.size === 'auto' ? oViewport[ options.axis ] : options.size;
            oThumb[ options.axis ] = Math.min( oTrack[ options.axis ], Math.max( 0, ( options.sizethumb === 'auto' ? ( oTrack[ options.axis ] * oContent.ratio ) : options.sizethumb ) ) );
        
            oScrollbar.ratio = options.sizethumb === 'auto' ? ( oContent[ options.axis ] / oTrack[ options.axis ] ) : ( oContent[ options.axis ] - oViewport[ options.axis ] ) / ( oTrack[ options.axis ] - oThumb[ options.axis ] );
            
            iScroll = ( sScroll === 'relative' && oContent.ratio <= 1 ) ? Math.min( ( oContent[ options.axis ] - oViewport[ options.axis ] ), Math.max( 0, iScroll )) : 0;
            iScroll = ( sScroll === 'bottom' && oContent.ratio <= 1 ) ? ( oContent[ options.axis ] - oViewport[ options.axis ] ) : isNaN( parseInt( sScroll, 10 ) ) ? iScroll : parseInt( sScroll, 10 );
            
            setSize();
        };

        function setSize(){
            var sCssSize = sSize.toLowerCase();

            oThumb.obj.css( sDirection, iScroll / oScrollbar.ratio );
            oContent.obj.css( sDirection, -iScroll );
            iMouse.start = oThumb.obj.offset()[ sDirection ];

            oScrollbar.obj.css( sCssSize, oTrack[ options.axis ] );
            oTrack.obj.css( sCssSize, oTrack[ options.axis ] );
            oThumb.obj.css( sCssSize, oThumb[ options.axis ] );
        }

        function setEvents(){
            if(!touchEvents){
                oThumb.obj.bind( 'mousedown', start );
                oTrack.obj.bind( 'mouseup', drag );
            } else {
                oViewport.obj[0].ontouchstart = function(event){   
                    if(1 === event.touches.length){
                        start( event.touches[ 0 ] );
                        event.stopPropagation();
                    }
                };
            }

            if( options.scroll && window.addEventListener ) {
                oWrapper[0].addEventListener( 'DOMMouseScroll', wheel, false );
                oWrapper[0].addEventListener( 'mousewheel', wheel, false );
            } else if( options.scroll ) {
                oWrapper[0].onmousewheel = wheel;
            }
        }

        function start( event ){
            $( "body" ).addClass( "noSelect" );

            var oThumbDir   = parseInt( oThumb.obj.css( sDirection ), 10 );
            iMouse.start    = sAxis ? event.pageX : event.pageY;
            iPosition.start = oThumbDir == 'auto' ? 0 : oThumbDir;
            
            if( !touchEvents ) {
                $( document ).bind( 'mousemove', drag );
                $( document ).bind( 'mouseup', end );
                oThumb.obj.bind( 'mouseup', end );
            } else {
                document.ontouchmove = function( event )
                {
                    event.preventDefault();
                    drag( event.touches[ 0 ] );
                };
                document.ontouchend = end;        
            }
        }

        function wheel( event ) {
            if( oContent.ratio < 1 ) {
                var oEvent = event || window.event
                ,iDelta = oEvent.wheelDelta ? oEvent.wheelDelta / 120 : -oEvent.detail / 3;

                iScroll -= iDelta * options.wheel;
                iScroll = Math.min( ( oContent[ options.axis ] - oViewport[ options.axis ] ), Math.max( 0, iScroll ));

                oThumb.obj.css( sDirection, iScroll / oScrollbar.ratio );
                oContent.obj.css( sDirection, -iScroll );

                if( options.lockscroll || ( iScroll !== ( oContent[ options.axis ] - oViewport[ options.axis ] ) && iScroll !== 0 ) )
                {
                    oEvent = $.event.fix( oEvent );
                    oEvent.preventDefault();
                }
            }
        }

        function drag( event ){
            if( oContent.ratio < 1 ){
                if( options.invertscroll && touchEvents ) {
                    iPosition.now = Math.min( ( oTrack[ options.axis ] - oThumb[ options.axis ] ), Math.max( 0, ( iPosition.start + ( iMouse.start - ( sAxis ? event.pageX : event.pageY ) ))));
                } else {
                     iPosition.now = Math.min( ( oTrack[ options.axis ] - oThumb[ options.axis ] ), Math.max( 0, ( iPosition.start + ( ( sAxis ? event.pageX : event.pageY ) - iMouse.start))));
                }

                iScroll = iPosition.now * oScrollbar.ratio;
                oContent.obj.css( sDirection, -iScroll );
                oThumb.obj.css( sDirection, iPosition.now );
            }
        }
        
        function end(){
            $( "body" ).removeClass( "noSelect" );
            $( document ).unbind( 'mousemove', drag );
            $( document ).unbind( 'mouseup', end );
            oThumb.obj.unbind( 'mouseup', end );
            document.ontouchmove = document.ontouchend = null;
        }

        return initialize();
    }
}(jQuery));

// Configure elements of the page
;(function($){
    $(function(){
        if($('.scroll-x').exist()){
            $('.scroll-x').tinyscrollbar({
                axis: 'x'
            }); 
        };
        if($('.scroll-y').exist()){ 
            $('.scroll-y').tinyscrollbar({
                axis: 'y'
            }); 
        };
        if($('.post').exist()){ 
            $('.post').blogpost({
            }); 
        };
        if ($("#login-btn").exist()){
            $("#login-btn").click(function(){
                $("#login-btn").postForm("#login","login",
                    $("#login-btn").jsonCallback(function(r){location.href=window.location.pathname;},$("#login").find(".error-hook"),"<div class='error-msg'>User Not Found</div>")
                );
            });
        };
        if ($("#signin-btn").exist()){
            $("#signin-btn").click(function(){
                $("#signin-btn").postForm("#signin","signin",
                    $("#signin-btn").jsonCallback(function(r){location.href=window.location.pathname;},$("#signin").find(".error-hook"),"<div class='error-msg'>Invalid User</div>")
                );
            });
        };
    });
}(jQuery));