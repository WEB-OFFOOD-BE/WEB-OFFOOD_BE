// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Bar Chart Example
var ctx = document.getElementById("myBarChart");
var m1 = document.getElementById("m1").innerHTML;
var m2 = document.getElementById("m2").innerHTML;
var m3 = document.getElementById("m3").innerHTML;
var m4 = document.getElementById("m4").innerHTML;
var m5 = document.getElementById("m5").innerHTML;
var m6 = document.getElementById("m6").innerHTML;
var m7 = document.getElementById("m7").innerHTML;
var m8 = document.getElementById("m8").innerHTML;
var m9 = document.getElementById("m9").innerHTML;
var m10 = document.getElementById("m10").innerHTML;
var m11 = document.getElementById("m11").innerHTML;
var m12 = document.getElementById("m12").innerHTML;
var myLineChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
    datasets: [{
      label: "Doanh thu",
      backgroundColor: "rgba(2,117,216,1)",
      borderColor: "rgba(2,117,216,1)",
      data: [m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12],
      // data: [50000, 50000, 50000, 50000, 50000, 50000,50000, 50000, 50000, 50000, 50000, 50000],
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: 'month'
        },
        gridLines: {
          display: false
        },
        ticks: {
          maxTicksLimit: 12
        }
      }],
      yAxes: [{
        ticks: {
          min: 0,
          max: 20000000,
          maxTicksLimit: 5
        },
        gridLines: {
          display: true
        }
      }],
    },
    legend: {
      display: false
    }
  }
});


