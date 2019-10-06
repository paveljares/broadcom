(function($) {
	
	window.App = {
		
		/* last send request - to cancel in case of faster user action than server */
		lastRequest : null,
		/* last send requests URL - to reduce duplicities */
		lastUrl : '',
		/* current page number */ 
		page : 0,
		/* current page size */
		size : 0,
		/* instance of GMap */ 
		map : null,
		/* current found items (cities) */
		data : [],
		/* current displayed markers on map - for next refresh*/
		markers : [],
			
		/**
		 * read version via REST and show in right bottom corner
		 */
		loadVersion : function() {
			$.get("./api/version", function(data) {
				$(".version").text(data);
			});
		},
		
		/**
		 * Load GMap API key, and the call initMap to init markers in there
		 */
		loadGmapApiKey : function() {
			var t=this;
			$.get("./api/config", function(data) {
				t.gmapApiKey=data.gmapApiKey;
				
				$('head').append($('<script async defer src="https://maps.googleapis.com/maps/api/js?key=' + t.gmapApiKey + '&callback=window.App.initMap"></script>'));

			});
		},
		
		/**
		 * Initialization of GMap, also show markers (if data are ready)
		 */
		initMap : function() {
			this.map = new google.maps.Map(document.getElementById('map'), {
				zoom: 2,
				center: {
					lat: 0, 
					lng: 0
				}
	        });

			this.showOnMap(this.data.items);
		},
	
		/**
		 * Method refresh markers on GMap (remove current and show new for cities in data)
		 */
		showOnMap : function(data) {
			var t=this;
			// if map is not initialized end
			if (!t.map) return;

			// remove markers
			for (var i=0; i<t.markers.length; i++) {
				t.markers[i].setMap(null);
			}
			t.markers=[];
			
			// create new markers
			for (var i=0; i<data.length; i++) {
				if (!data[i].gps) continue;
				var marker = new google.maps.Marker({
			        position: new google.maps.LatLng(data[i].gps.latitude, data[i].gps.longitude),
			        map: t.map,
			        title: data[i].name + ', ' + data[i].country
		        });
				t.markers.push(marker);
			}
			$('.table .left .show-all').toggleClass('hidden', data.length>1);
		},
		
		/**
		 * Method show cities and refresh state of page
		 */
		show : function(data) {
			if (!data) return;
			
			var $e=$('#citiesList tbody');
			
			$e.empty();
			
			// method to get HTML of table's row
			var getRow=function(x) {
				var $r=$('<tr><td class="country"></td><td class="city"></td><td class="population"></td></tr>');
				$r.find('td.country').text(x.country);
				$r.find('td.city').text(x.name);
				$r.find('td.population').text(x.population);
				return $r;
			}
			
			// create table with cities
			for (var i=0; i<data.items.length; i++) {
				var item=data.items[i];
				$e.append(getRow(item));
			}
			
			// show markers of cities
			this.showOnMap(data.items);
			
			// set right visibility of pagging
			$('.table .left .prev a').toggleClass('hidden', data.offset == 0);
			$('.table .left .next a').toggleClass('hidden', data.size < this.size + this.size);
			$('.table .left .page').text(this.page + 1);
		},
		
		/**
		 * Search cities by pattern
		 */
		search : function(pattern) {
			var t=this;
			
			// URL for searching
			var url='./map/search/' + (pattern || $('#search').val() || '') + '?page=' + t.page + '&size=' + t.size
			
			// ignore, if query is the same like previous one
			if (t.lastUrl===url) return;
			
			// cancel previous request, if it is still in progress
			if (t.lastRequest && t.lastRequest.readyState !== 4) t.lastRequest.abort();
			
			t.lastRequest=$.get(url, function(data) {
				t.data=data;
				t.show(data);
			});
		},
		
		/**
		 * Initialization of web page
		 */
		initSearch : function() {
			var t=this;
			/**
			 * In case of any customers input search cities
			 */
			$('#search').change(function() {
				t.page=0;
				t.search($(this).val());
			});
			$('#search').keyup(function() {
				t.page=0;
				t.search($(this).val());
			});
			
			/**
			 * Events of using the pagging
			 */
			var $size=$('.table .left select[name="size"]');
			$size.change(function() {
				t.page=0;
				t.size=Number($(this).val());
				t.search();
			});
			$('.table .left .prev a').click(function() {
				$(this).addClass('hidden');
				t.page--;
				t.search();
			});
			$('.table .left .next a').click(function() {
				$(this).addClass('hidden');
				t.page++;
				t.search();
			});
			
			/**
			 * Showing just one or all markers on the map
			 */
			$('.table .left').on('click', 'tr td', function() {
				t.showOnMap([t.data.items[$(this).closest('tr').index()]]);
			});
			$('.table .left .show-all').click(function() {
				t.showOnMap(t.data.items);
			});
			
			/**
			 * First search for the data
			 */
			t.size=Number($size.val());
			t.search();
		},
		
		/**
		 * Method to fully initialization
		 */
		init : function() {
			this.loadVersion();
			this.loadGmapApiKey();
			this.initSearch();
			this.search();
		}
	
	};

})(jQuery);
