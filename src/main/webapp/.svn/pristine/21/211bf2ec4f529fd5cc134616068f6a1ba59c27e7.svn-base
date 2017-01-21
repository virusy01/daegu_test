/**
 * Created by jwm_000 on 2015-10-13.
 */
var RadarChart =  (function (window, d3, undefined){
    var itemRadius = 4,
        width =  400,
        height = 400,
        factor = 1,
        factorLegend = .85,
        levels = 5,
        radians = 2 * Math.PI,
        opacityArea = 0.5,
        toRight = 5,
        translateX = 80,
        translateY = 40,
        extraWidthX = 140,
        extraWidthY = 140,
        fills = ["#f76869", "#5da2e6", "#00bf88", "#f4c100", "#bf5bf3", "#3eb5ac" , "#f58410", "#f94a76", "#97d54c", "f0a0dd" ,
                    "#b2485f", "#9b9c9a", "#49ddff", "#306555", "#003366", "#7fbcff", "#ba8d72", "#ba8d72", "#8c7721", "#6db5a1"],
        categoryField = "label",
        valueField = "value";

    function RadarChart(){

        this.options = {};

        this.draw = function() {

            var options = this.options;
            options.itemRadius = options.itemRadius || itemRadius;
            options.width = options.width || width;
            options.height = options.height || height;
            options.factor = options.factor || factor;
            options.factorLegend = options.factorLegend || factorLegend;
            options.levels = options.levels || levels;
            options.radians = options.radians || radians;
            options.opacityArea = options.opacityArea || opacityArea;
            options.toRight = options.toRight || toRight;
            options.translateX = options.translateX || translateX;
            options.translateY = options.translateY || translateY;
            options.extraWidthX = options.extraWidthX || extraWidthX;
            options.extraWidthY = options.extraWidthY || extraWidthY;
            options.fills = options.fills || fills;
            options.categoryField = options.categoryField || categoryField;
            options.valueField = options.valueField || valueField;
            var data = options.data;

            if (!data || data.length < 1){
                return;
            }

            var color = d3.scale.ordinal()
                .range(options.fills);

            var holder = options.contentHolder;
                options.maxValue = Math.max(options.maxValue, d3.max(data, function (d) {
                    return d3.max(d.map(function (o) {
                        return o[options.valueField];
                    }))
                }));

            var allAxis = (data[0].map(function (d, i) {
                return d[options.categoryField]
            }));
            var total = allAxis.length;
            var radius = options.factor * Math.min(options.width / 2, options.height / 2);
            var format = d3.format('%');
            d3.select(holder).select("svg").remove();

            var svg = d3.select(holder)
                .append("svg")
                .attr("width", options.width + options.extraWidthX)
                .attr("height", options.height + options.extraWidthY)
                .append("g")
                .attr("transform", "translate(" + options.translateX + "," + options.translateY + ")");


            var tooltip;

            //Circular segments
            for (var j = 0; j < options.levels; j++) {
                var levelFactor = options.factor * radius * ((j + 1) / options.levels);
                svg.selectAll(".levels")
                    .data(allAxis)
                    .enter()
                    .append("line")
                    .attr("x1", function (d, i) {
                        return levelFactor * (1 - options.factor * Math.sin(i * options.radians / total));
                    })
                    .attr("y1", function (d, i) {
                        return levelFactor * (1 - options.factor * Math.cos(i * options.radians / total));
                    })
                    .attr("x2", function (d, i) {
                        return levelFactor * (1 - options.factor * Math.sin((i + 1) * options.radians / total));
                    })
                    .attr("y2", function (d, i) {
                        return levelFactor * (1 - options.factor * Math.cos((i + 1) * options.radians / total));
                    })
                    .attr("class", "line")
                    .style("stroke", "grey")
                    .style("stroke-opacity", "0.75")
                    .style("stroke-width", "0.3px")
                    .attr("transform", "translate(" + (options.width / 2 - levelFactor) + ", " + (options.height / 2 - levelFactor) + ")");
            }

            //Text indicating at what % each level is
            for (var j = 0; j < options.levels; j++) {
                var levelFactor = options.factor * radius * ((j + 1) / options.levels);
                svg.selectAll(".levels")
                    .data([1]) //dummy data
                    .enter()
                    .append("text")
                    .attr("x", function (d) {
                        return levelFactor * (1 - options.factor * Math.sin(0));
                    })
                    .attr("y", function (d) {
                        return levelFactor * (1 - options.factor * Math.cos(0));
                    })
                    .attr("class", "legend")
                    .style("font-family", "sans-serif")
                    .style("font-size", "10px")
                    .attr("transform", "translate(" + (options.width / 2 - levelFactor + options.toRight) + ", " + (options.height / 2 - levelFactor) + ")")
                    .attr("fill", "#737373")
                    .text((j+1) * (options.maxValue / options.levels));
            }

            var series = 0;

            var axis = svg.selectAll(".axis")
                .data(allAxis)
                .enter()
                .append("g")
                .attr("class", "axis");

            axis.append("line")
                .attr("x1", options.width / 2)
                .attr("y1", options.height / 2)
                .attr("x2", function (d, i) {
                    return options.width / 2 * (1 - options.factor * Math.sin(i * options.radians / total));
                })
                .attr("y2", function (d, i) {
                    return options.height / 2 * (1 - options.factor * Math.cos(i * options.radians / total));
                })
                .attr("class", "line")
                .style("stroke", "grey")
                .style("stroke-width", "1px");

            axis.append("text")
                .attr("class", "legend")
                .text(function (d) {
                    return d
                })
                .style("font-family", "sans-serif")
                .style("font-size", "11px")
                .attr("text-anchor", "middle")
                .attr("dy", "1.5em")
                .attr("transform", function (d, i) {
                    return "translate(0, -10)"
                })
                .attr("x", function (d, i) {
                    return options.width / 2 * (1 - options.factorLegend * Math.sin(i * options.radians / total)) - 60 * Math.sin(i * options.radians / total);
                })
                .attr("y", function (d, i) {
                    return options.height / 2 * (1 - Math.cos(i * options.radians / total)) - 20 * Math.cos(i * options.radians / total);
                });


            var dataValues;
            data.forEach(function (y, x) {
                dataValues = [];
                svg.selectAll(".nodes")
                    .data(y, function (d, i) {
                        dataValues.push([
                            options.width / 2 * (1 - (parseFloat(Math.max(d[options.valueField], 0)) / options.maxValue) * options.factor * Math.sin(i * options.radians / total)),
                            options.height / 2 * (1 - (parseFloat(Math.max(d[options.valueField], 0)) / options.maxValue) * options.factor * Math.cos(i * options.radians / total))
                        ]);
                    });

                dataValues.push(dataValues[0]);
                svg.selectAll(".area")
                    .data([dataValues])
                    .enter()
                    .append("polygon")
                    .attr("class", "radar-chart-serie" + series)
                    .style("stroke-width", "2px")
                    .style("stroke", color(series))
                    .attr("points", function (d) {
                        var str = "";
                        for (var pti = 0; pti < d.length; pti++) {
                            str = str + d[pti][0] + "," + d[pti][1] + " ";
                        }
                        return str;
                    })
                    .style("fill", function (j, i) {
                        return color(series)
                    })
                    .style("fill-opacity", options.opacityArea)
                    .on('mouseover', function (d) {
                        z = "polygon." + d3.select(this).attr("class");
                        svg.selectAll("polygon")
                            .transition(200)
                            .style("fill-opacity", 0.1);
                        svg.selectAll(z)
                            .transition(200)
                            .style("fill-opacity", .7);
                    })
                    .on('mouseout', function () {
                        svg.selectAll("polygon")
                            .transition(200)
                            .style("fill-opacity", options.opacityArea);
                    });
                series++;
            });
            series = 0;

            var d3Tooltip = d3.select(".tip");
            if (d3Tooltip.empty()) {
                d3Tooltip = d3.select("body")
                    .append("div")
                    .attr("class", "tip");
            }

            var newX, newY, z;
            data.forEach(function (y, x) {
                svg.selectAll(".nodes")
                    .data(y).enter()
                    .append("circle")
                    .attr("class", "radar-chart-serie" + series)
                    .attr('r', options.itemRadius)
                    .attr("cx", function (j, i) {
                        dataValues.push([
                            options.width / 2 * (1 - (parseFloat(Math.max(j[options.valueField], 0)) / options.maxValue) * options.factor * Math.sin(i * options.radians / total)),
                            options.height / 2 * (1 - (parseFloat(Math.max(j[options.valueField], 0)) / options.maxValue) * options.factor * Math.cos(i * options.radians / total))
                        ]);
                        return options.width / 2 * (1 - (Math.max(j[options.valueField], 0) / options.maxValue) * options.factor * Math.sin(i * options.radians / total));
                    })
                    .attr("cy", function (j, i) {
                        return options.height / 2 * (1 - (Math.max(j[options.valueField], 0) / options.maxValue) * options.factor * Math.cos(i * options.radians / total));
                    })
                    .attr("data-id", function (j) {
                        return j[options.categoryField];
                    })
                    .style("fill", color(series)).style("fill-opacity", .9)
                    .on('mouseover', function (d) {

                        var globalX = d3.event.pageX;
                        var globalY = d3.event.pageY;

                        newX = parseFloat(d3.select(this).attr('cx')) - 10;
                        newY = parseFloat(d3.select(this).attr('cy')) - 5;

                        var tooltipHeight = parseFloat(d3Tooltip.style("height"));
                        var tooltipWidth = parseFloat(d3Tooltip.style("width"));
                        var tooltip = d3Tooltip.node();

                        var dataTipStr;
                        if (options.dataTipFunction){
                            options.dataTipFunction(d, tooltip);
                        }

                        d3Tooltip.style("left", globalX - tooltipWidth/2 + "px")
                            .style("top", globalY - tooltipHeight - 30 + "px")
                            .style("visibility", "visible");

                        z = "polygon." + d3.select(this).attr("class");
                        svg.selectAll("polygon")
                            .transition(200)
                            .style("fill-opacity", 0.1);
                        svg.selectAll(z)
                            .transition(200)
                            .style("fill-opacity", .6);
                    })
                    .on('mouseout', function () {
                        d3Tooltip.style("visibility", "hidden");
                        svg.selectAll("polygon")
                            .transition(200)
                            .style("fill-opacity", options.opacityArea);

                    })
                    .append("title")
                    .text(function (j) {
                        return Math.max(j[options.valueField], 0)
                    });

                series++;
            });
        }
    }

    return RadarChart;
})(window, d3, undefined);
