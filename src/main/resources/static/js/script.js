let center = [58.01140385358768,56.229214161509084];

var myMap;

function init() {
    myMap = new ymaps.Map('map', {
        center: center,
        zoom: 12
    });
    for (let i=0; i<addresses.length; i++) {
        let placemarker = new ymaps.Placemark([addresses[i].lat, addresses[i].lon], {
            balloonContent: `
                <div>
                    <p>Город: ${addresses[i].city}</p>
                    <p>Улица: ${addresses[i].street}</p>
                    <p>Здание: ${addresses[i].build}</p>
                    <p>Подъезд: ${addresses[i].ent}</p>
                </div>
            `
        }, {
            iconLayout: 'default#image',
            iconImageHref: 'https://cdn-icons-png.flaticon.com/512/684/684908.png',
            iconImageSize: [30, 30],
            iconImageOffset: [-20, -30]
        })
        myMap.geoObjects.add(placemarker);
    }

    /**ymaps.geocode('Пермь, Челюскинцев, 23', {
        results: 1
    }).then(function (res) {
        var firstGeoObject = res.geoObjects.get(0),
            coords = firstGeoObject.geometry.getCoordinates(),
            bounds = firstGeoObject.properties.get('boundedBy');

        firstGeoObject.options.set('preset', 'islands#darkBlueDotIconWithCaption');
        firstGeoObject.properties.set('iconCaption', firstGeoObject.getAddressLine());

        myMap.geoObjects.add(firstGeoObject);
        myMap.setBounds(bounds, {
            checkZoomRange: true
        });

        console.log('Все данные геообъекта: ', firstGeoObject.properties.getAll());

        console.log('Метаданные ответа геокодера: ', res.metaData);
        console.log('Метаданные геокодера: ', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData'));

        console.log('precision', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData.precision'));

        console.log('Тип геообъекта: %s', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData.kind'));
        console.log('Название объекта: %s', firstGeoObject.properties.get('name'));
        console.log('Описание объекта: %s', firstGeoObject.properties.get('description'));
        console.log('Полное описание объекта: %s', firstGeoObject.properties.get('text'));
        console.log('\nГосударство: %s', firstGeoObject.getCountry());
        console.log('Населенный пункт: %s', firstGeoObject.getLocalities().join(', '));
        console.log('Адрес объекта: %s', firstGeoObject.getAddressLine());
        console.log('Наименование здания: %s', firstGeoObject.getPremise() || '-');
        console.log('Номер здания: %s', firstGeoObject.getPremiseNumber() || '-');
    });*/
}

function getCoords() {
    var address = document.getElementById("city").value +
        document.getElementById("street").value +
        document.getElementById("build").value;
    ymaps.geocode(address, {
        results: 1
    }).then(function (res) {
        var firstGeoObject = res.geoObjects.get(0),
            coords = firstGeoObject.geometry.getCoordinates();
        document.getElementById('lat').value = coords[0];
        document.getElementById('lon').value = coords[1];
    });
}

ymaps.ready(init);

function mapZoom() {
    var address = document.getElementById("check_city").value +
        document.getElementById("check_street").value +
        document.getElementById("check_build").value;
    ymaps.geocode(address, {
        results: 1
    }).then(function (res) {
        var firstGeoObject = res.geoObjects.get(0),
            coords = firstGeoObject.geometry.getCoordinates();
        myMap.setCenter(coords, 18);
    });
}

