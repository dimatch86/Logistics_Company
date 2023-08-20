$(function(){

    const appendOrder = function(data){
        var orderCode = '<a href="#" class="order-link" data-id="' +
            data.id + '">' + data.id + '.' + data.description + ' == ' + data.status + '</a><br>';
        $('#order-list')
            .append('<div>' + orderCode + '</div>');
    };

   //Loading tasks on load page
   /*$.get('/tasks/', function(response)
    {
        for(i in response) {
            appendTask(response[i]);
        }
    });*/

    //Show adding task form
    $('#show-add-order-form').click(function(){
        $('#order-form').css('display', 'flex');
    });

    //Show deleting task form
        $('#show-delete-order-form').click(function(){
            $('#del-order-form').css('display', 'flex');
        });

    //Closing adding task form
    $('#order-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Closing deleting task form
        $('#del-order-form').click(function(event){
            if(event.target === this) {
                $(this).css('display', 'none');
            }
        });

    //Getting task
    $(document).on('click', '.order-link', function(){

        var link = $(this);
        var orderId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/company/api/order/' + orderId,
            success: function(response)
            {
                var code = '<span>Статус:' + response.status + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status === 404) {
                    alert('Заказ не найден!');
                }
            }
        });
        return false;
    });

    //Adding task
    $('#save-order').click(function()
    {
        var data = $('#order-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/company/api/order',
            //dataType: 'json',
            data: data,
            success: function(response)
            {
                $('#order-form').css('display', 'none');
                var order = {};
                order.id = response;
                var dataArray = $('#order-form form').serializeArray();
                for(i in dataArray) {
                    order[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendOrder(order);
            }
        });
        return false;
    });

    //Deleting task
        $('#delete-order').click(function(){
        var orderId = $('#id').val();
            $.ajax({
                method: "DELETE",
                url: '/tasks/' + orderId,
                success: function(data)
                {
                $('#del-order-form').css('display', 'none');
                var deletedOrder = $('[data-id="' + orderId + '"]');
                deletedOrder.remove();
                },
                error: function(response)
                {
                    if(response.status === 500) {
                        alert('Заказ не найден!');
                    }
                }
            });
            return false;
        });

        //DeletingAll tasks
                $('#deleteAll').click(function(){
                alert('Подтвердите');

                    $.ajax({
                        method: "DELETE",
                        url: '/tasks/',
                        success: function(data)
                        {
                        $('.order-link').remove();
                        },
                        error: function(response)
                        {
                            if(response.status === 404) {
                                alert('Заказ не найден!');
                            }
                        }
                    });
                    return false;
                });
});