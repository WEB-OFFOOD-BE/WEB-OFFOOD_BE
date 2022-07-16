// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Pie Chart Example
var ctx = document.getElementById("myPieChart");
var ctxx = document.getElementById("myPieChart1");
var m1 = document.getElementById("c1").innerHTML;
var m2 = document.getElementById("c2").innerHTML;
var m3 = document.getElementById("c3").innerHTML;
var y1 = document.getElementById("y1").innerHTML;
var y2 = document.getElementById("y2").innerHTML;
var y3 = document.getElementById("y3").innerHTML;
var n1 = parseInt(m1);
var n2 = parseInt(m2);
var n3 = parseInt(m3);
var yy1 = parseInt(y1);
var yy2 = parseInt(y2);
var yy3 = parseInt(y3);
var tong = n1 + n2 +n3;
var tongy = yy1 + yy2 +yy3;
var tc = (n1/tong)*100;
var patc = parseFloat(tc);
tc = Math.round(patc * 100)/100;
var ye1 = (yy1/tongy)*100;
var patcy = parseFloat(ye1);
ye1 = Math.round(patcy * 100)/100;
var hd = (n2/tong)*100;
var pahd = parseFloat(hd);
hd = Math.round(pahd * 100)/100;
var ye2 = (yy2/tongy)*100;
var patcyy = parseFloat(ye2);
ye2 = Math.round(patcyy * 100)/100;
var kd = (n3/tong)*100;
var pakd = parseFloat(kd);
kd = Math.round(pakd * 100)/100;
var ye3 = (yy3/tongy)*100;
var patcyyy = parseFloat(ye3);
ye3 = Math.round(patcyyy * 100)/100;
var myPieChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: ["Ca sáng", "Ca chiều", "Ca tối" ],
    datasets: [{
      data: [tc, hd, kd],
      backgroundColor: ['#007bff', '#dc3545', '#ffc107'],
    }],
  },
});
var myPieChartt = new Chart(ctxx, {
  type: 'pie',
  data: {
    labels: ["Năm 2019", "Năm 2020", "Năm 2021" ],
    datasets: [{
      data: [ye1, ye2, ye3],
      backgroundColor: ['#007bff', '#dc3545', '#ffc107'],
    }],
  },
});
