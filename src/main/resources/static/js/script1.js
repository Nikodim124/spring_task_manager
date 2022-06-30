let center = [58.01140385358768,56.229214161509084];

var myMap;

function init() {
    myMap = new ymaps.Map('map', {
        center: center,
        zoom: 12
    });
    console.log(appeals);
    for (let i=0; i<appeals.length; i++) {
        let placemarker = new ymaps.Placemark([appeals[i]['address'].lat, appeals[i]['address'].lon], {
            balloonContent: `
                <div>
                    <p>Адрес: г. ${appeals[i]['address'].city}, ул. ${appeals[i]['address'].street}, ${appeals[i]['address'].build}, подъезд ${appeals[i]['address'].ent}, кв. ${appeals[i]['appart']}</p>
                    <p>Дата: ${appeals[i]['taskdate'].slice(0,10)}</p>
                    <p>Период: ${appeals[i]['tasktime']['time']}</p>
                    <p>Комментарий: ${appeals[i]['comment']}</p>
                </div>
            `
        }, {
            iconLayout: 'default#image',
            iconImageHref: 'https://cdn-icons-png.flaticon.com/512/684/684908.png',
            iconImageSize: [40, 40],
            iconImageOffset: [-20+i, -30+i]
        })
        myMap.geoObjects.add(placemarker);
    }
}

ymaps.ready(init);

function upd() {
    init();
}
