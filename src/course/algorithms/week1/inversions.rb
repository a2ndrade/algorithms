!#/usr/bin/env ruby

$count = 0

def sort(a)
  sort0(a)
end

def sort0(a)
  return a if a.size < 2
  half = a.size/2
  left = sort0(a[0,half])
  right = sort0(a[half,a.size-half])
  merge(left, right)
end

def merge(left, right)
  l=r=0
  o = []
  while l < left.size && r < right.size
    if left[l] < right[r]
      o.push(left[l])
      l = l.succ
    else
      o.push(right[r])
      $count = $count + left.size - l
      r = r.succ
    end
  end
  if l >= left.size
    while r < right.size
      o.push(right[r])
      r = r.succ
    end
  end
  if r >= right.size
    while l < left.size
      o.push(left[l])
      l = l.succ
    end
  end
  o
end


a=File.open("IntegerArray.txt").map{|line|line.to_i}
#print sort([12,19,12,1,45,43,2,1])
#a=[1,3,5,2,4,6]
print sort(a),"\n"
print $count


