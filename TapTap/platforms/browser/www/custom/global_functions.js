/**
 * Created by Luis on 28/4/16.
 */

// Disable double tap-zoom
(function($) {
    var count = 0;
    $.fn.nodoubletapzoom = function() {
        $(this).bind('touchstart', function preventZoom(e){
            var t2 = e.timeStamp;
            var t1 = $(this).data('lastTouch') || t2;
            var dt = t2 - t1;
            var fingers = e.originalEvent.touches.length;
            $(this).data('lastTouch', t2);
            if (!dt || dt > 500 || fingers > 1){
                return; // not double-tap
            }
            if(!can_zoom){
                e.preventDefault();
            }
            // double tap - prevent the zoom
            // also synthesize click events we just swallowed up
            $(e.target).trigger('tap');
        });
    };
})(jQuery);

$(document).on('pageinit',"body", function() {
    $("body").nodoubletapzoom();
});


