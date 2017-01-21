/**
 * Created by jwm_000 on 2015-10-06.
 */

var HStackChart = (function (window, d3, undefined){

    var padding = {left: 150, right: 200, top: 40, bottom: 30},
        width = 500,
        height = 500,
        categoryField = "label",
        fills = ['#2fd05f', '#5cefa1', '#f46464', '#fca6a6', '#44cddb', '#6be8ee', '#9ef5f7', '#fa77c3', '#fdb9ea', '#ffcdf1'];

    var HStackChart = function () {
        this.options = {};

        this.draw = function (showAnimation) {

            if (showAnimation === undefined){
                showAnimation = true;
            }

            var options = this.options;

            options.categoryField = options.categoryField || categoryField;
            options.padding = options.padding || padding;
            options.fills = options.fills || fills;

            options.width = options.width || width;
            options.height = options.height || height;

            var data = options.data;

            if (!data || data.length < 1) {
                return;
            }

            var holder = options.contentHolder;

            var svgWidth = options.width + options.padding.left + options.padding.right;
            var svgHeight = options.height + options.padding.top + options.padding.bottom;

            var innerWidth = svgWidth - options.padding.left - options.padding.right;
            var innerHeight = svgHeight - options.padding.top - options.padding.bottom;


            d3.select(holder).select("svg").remove();
            var svg = d3.select(holder).append("svg");

            if (options.percentWidth) {
                svg.style("width", options.percentWidth + "%");
                innerWidth = svg.node().clientWidth - options.padding.left - options.padding.right;
            }else {
                svg.style("width", svgWidth + "px");
            }

            if (options.percentHeight){
                svg.style("height", options.percentHeight + "%");
                innerHeight = svg.node().clientHeight - options.padding.top - options.padding.bottom;
            }else{
                svg.style("height", svgHeight + "px");
            }

            var viewport = svg.append("g")
                .attr("transform", "translate(" + options.padding.left + "," + options.padding.top + ")");


            var xScale = d3.scale.linear()
                .rangeRound([0, innerWidth]);

            var yScale = d3.scale.ordinal()
                .rangeRoundBands([0, innerHeight], 0.25, 0.1);

            var color = d3.scale.ordinal()
                .range(options.fills);

            var xAxis = d3.svg.axis()
                .scale(xScale)
                .orient("top")
                .innerTickSize(4)
                .outerTickSize(0);


            var yAxis = d3.svg.axis()
                .scale(yScale)
                .orient("left")
                .innerTickSize(4)
                .outerTickSize(0);

            var d3Tooltip = d3.select(".hcharttip");
            if (d3Tooltip.empty()) {
                d3Tooltip = d3.select("body")
                    .append("div")
                    .attr("class", "hcharttip");
            }


            // series
            var seriesNames;
            if (options.series){
                seriesNames = options.series.map(function (d) {
                    return d.name;
                });
            }else {
                seriesNames = d3.keys(data[0]).filter(function (key) {
                    return key != options.categoryField
                });
            }

            color.domain(seriesNames);

            data.forEach(function (d) {
                var y0 = 0;

                d.stackParts = color.domain().map(function (stackPart) {
                    return {stackPart: stackPart, y0: y0, y1: y0 += +d[stackPart], parent:d};
                });
                d.total = d.stackParts[d.stackParts.length - 1].y1;
            });

            yScale.domain(data.map(function (d) {
                return {toString : function(){
                        return d[options.categoryField];
                    }, data:d
                };
            }));

            xScale.domain([0, options.maxValue || d3.max(data, function (d) {
                return d.total;
            })]);

            var categories = viewport.selectAll(".category")
                .data(data)
                .enter()
                .append("g")
                .attr("class", "g")
                .attr("transform", function (d) {
                    return "translate(0," + yScale(d[options.categoryField]) + ")";
                })
                .on("click", function(d, i){
                    if (options.categoryClickFunction){
                        options.categoryClickFunction(d, i);
                    }
                });


            var categoryData = categories.selectAll("rect")
                .data(function (d) {
                    return d.stackParts.reverse();
                });

            var duration = 1000;

            if (showAnimation) {
                categoryData.enter()
                    .append("rect")
                    .attr("height", yScale.rangeBand())
                    .attr("x", 0)
                    .attr("width", 0)
                    .style("fill", "#fff")
                    .transition()
                    .duration(duration)
                    .attr("width", function (d) {
                        return xScale(d.y1);
                    })
                    .style("fill", function (d) {
                        return color(d.stackPart);
                    });
            }else{
                categoryData.enter()
                    .append("rect")
                    .attr("height", yScale.rangeBand())
                    .attr("x", 0)
                    .attr("width", function (d) {
                        return xScale(d.y1);
                    })
                    .style("fill", function (d) {
                        return color(d.stackPart);
                    });
            }

            if (options.showDataTip) {
                categoryData.on("mousemove", function (d, i) {
                    var x = d3.event.pageX;
                    var y = d3.event.pageY;

                    var tooltipHeight = parseFloat(d3Tooltip.style("height"));
                    var tooltipWidth = parseFloat(d3Tooltip.style("width"));
                    var tooltip = d3Tooltip.node();

                    d3Tooltip.style("left", x - tooltipWidth / 2 + "px")
                        .style("top", y - tooltipHeight - 25 + "px")
                        .style("visibility", "visible");

                    if (options.dataTipFunction){
                        options.dataTipFunction(d, d.parent, tooltip, color(d.stackPart));
                    } else {
                        d3Tooltip.text(d.y1 - d.y0);
                    }
                })
                .on("mouseout", function () {
                    d3Tooltip.style("visibility", "hidden");
                });
            }

            var timeout;

            if (!showAnimation){
                showLabel();
            } else {
                timeout = setTimeout(showLabel, duration);
            }

            function showLabel() {
                // top label
                viewport.selectAll(".label")
                    .data(data)
                    .enter()
                    .append("text")
                    .attr("x", function (d, i) {
                        return xScale(d.total) + 6;
                    })
                    .text(function (d, i) {
                        return d.total;
                    })
                    .style("fill", "#000000")
                    .attr("y", function (d, i) {
                        // y category 위치 + barWidth/2
                        var textHeight = this.getBBox().height;
                        return yScale(d[options.categoryField]) + (yScale.rangeBand() / 2) + (textHeight/4);
                    });

                if (timeout){
                    clearTimeout(timeout);
                }
            }

            // legend
            var legendX = innerWidth + options.padding.left + 30;
            var legendY = options.padding.top;
            var legendSize = 14;
            var i;

            for (i = 0; i<options.series.length; i++) {
                svg.append("rect")
                    .attr("height", legendSize)
                    .attr("width", legendSize)
                    .attr("x", legendX)
                    .attr("y", legendY)
                    .style("fill", color(options.series[i].name));

                svg.append("text")
                    .attr("x", legendX + legendSize + 2)
                    .style("font-size", 11)
                    .attr("y", function(){
                        return legendY + 10;
                    })
                    .text(options.series[i].label);

                legendY += legendSize + 4;
            }


            // y 축
            viewport.append("g")
                .attr("class", "y axis")
                .style("cursor", "pointer")
                .call(yAxis);

            // x 축
            viewport.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0,0)")
                .call(xAxis);

        }
    }

    return HStackChart;
})(window, d3, undefined);
