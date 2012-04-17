!#/usr/bin/env ruby

k = []
for i in 1..1600
  vertices=IO.read('kargerAdj.txt').split("\r\n").reduce([nil]){|a,line|a.push(line.strip.split(/\s+/).map{|i|i.to_i})}
  edges=vertices.drop(1).reduce([]){|a,v| first=v.first; v.drop(1).each{|vi| a.push([first,vi])}; a}

  if i == 1
    puts "Number of Vertices => #{vertices.drop(1).size}"
    puts "Number of Edges => #{edges.size}"
  end

  while vertices.reject{|v|v.nil?}.size > 2
   # select random edge
   random_edge = (edges.size*rand)
   # get edge's vertices
   tail = edges[random_edge].first
   head = edges[random_edge].last
   # next iteration if tail and head are the same
   next if tail == head

   # contract vertices
   # remove edge linking the 2 vertices
   combined = (vertices[tail]+vertices[head]).reject{|i|i == tail || i == head}
   # null-out existing vertices
   vertices[tail]=nil
   vertices[head]=nil
   # add combined list into a new list
   new_vertice = vertices.size
   vertices[new_vertice]=[new_vertice].concat(combined)
   # update pointers to tail and head to point to new combined list
   vertices.map! do |v|
    next nil if v.nil?
    first = v.first
    v = [first].concat(v.drop(1).map!{|vi| next new_vertice if vi == tail || vi == head; vi}.reject{|vi|vi==first})
   end
   edges.map! do |v|
    v.map!{|vi| next new_vertice if vi == tail || vi == head; vi}
   end
   # remove current edge
   edges.slice!(random_edge,1)
  end

  vertices.reject{|v|v.nil?}.map{|vi|vi.drop(1)}.each do |list|
   print list,"\n"
  end

  k.push(vertices.reject{|v|v.nil?}.first.drop(1).size)
end

puts "Min cut is => #{k.min}"
