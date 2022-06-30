const draggables = document.querySelectorAll('.draggable')
const containers = document.querySelectorAll('.container')

let startpoint;
let endpoint;

draggables.forEach(draggable => {
    draggable.addEventListener('dragstart', () => {
        startpoint = draggable.parentElement;
        draggable.classList.add('dragging')
    })

    draggable.addEventListener('dragend', () => {
        endpoint = draggable.parentElement;
        var $form = $(draggable).children(".form_draggable");
        if (startpoint === containers[0] && endpoint === containers[1]) {
            draggable.classList.add('draggable_active')
            $("#cover").css('display', 'block')
            $($form).css('display', 'block');
            $($form).children(".set_employee").on('click', function() {
                let choose = $($form).children(".field_employee").val()
                if (choose === '') {
                    $("#cover").css('display', 'none');
                    draggable.classList.remove('draggable_active');
                    $($form).css('display','none');
                    $("#con1").append(draggable);
                } else {
                    var data_id = $($form).children(".field_id");
                    $.ajax({
                        type: 'GET',
                        url: '/appeals/set_employee',
                        data: {
                            id: data_id.val(),
                            emp_id: choose
                        },
                        success: function (data) {
                            $(draggable).children('.by_employee').text('Ответственное лицо: '+data.employee.lastname+' '+data.employee.firstname+' '+data.employee.upname);
                        },
                        error: function(data) {
                            console.log('errors');
                        }
                    });
                    $("#cover").css('display', 'none');
                    $($form).css('display','none');
                    draggable.classList.remove('draggable_active');
                }
            })
            $("#cover").on('click', function() {
                $("#cover").css('display', 'none');
                draggable.classList.remove('draggable_active');
                $($form).css('display','none');
                $("#con1").append(draggable);
            })
        } else if (startpoint === containers[1] && endpoint === containers[0]) {
            var data_id = $($form).children(".field_id");
            $.ajax({
                type: 'GET',
                url: '/appeals/null_employee',
                data: {
                    id: data_id.val()
                },
                success: function (data) {
                    $(draggable).children('.by_employee').text("Ответсвенное лицо: Не назначено");
                },
                error: function(data) {
                    console.log('errors');
                }
            });
        }
        draggable.classList.remove('dragging')
    })
})

containers.forEach(container => {
    container.addEventListener('dragover', e => {
        e.preventDefault()
        const afterElement = getDragAfterElement(container, e.clientY)
        const draggable = document.querySelector('.dragging')
        if (afterElement == null) {
            container.appendChild(draggable)
        } else {
            container.insertBefore(draggable, afterElement)
        }
    })
})

function getDragAfterElement(container, y) {
    const draggableElements = [...container.querySelectorAll('.draggable:not(.dragging)')]
    return draggableElements.reduce((closest, child) => {
        const box = child.getBoundingClientRect()
        const offset = y - box.top - box.height / 2
        if (offset < 0 && offset > closest.offset) {
            return { offset: offset, element: child }
        } else {
            return closest
        }
        return closest
    }, { offset: Number.NEGATIVE_INFINITY }).element
}