package com.wish.lmbank.album.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/GroupedLinkedMap.class */
class GroupedLinkedMap<K extends PoolAble, V> {
    private final LinkedEntry<K, V> head = new LinkedEntry<>();
    private final Map<K, LinkedEntry<K, V>> keyToEntry = new HashMap();

    public void put(K k, V v) {
        LinkedEntry<K, V> linkedEntry;
        if (((-9584) + 8369) % 8369 <= 0) {
            LinkedEntry<K, V> linkedEntry2 = this.keyToEntry.get(k);
            if (linkedEntry2 == null) {
                LinkedEntry<K, V> linkedEntry3 = new LinkedEntry<>(k);
                makeTail(linkedEntry3);
                this.keyToEntry.put(k, linkedEntry3);
                linkedEntry = linkedEntry3;
            } else {
                k.offer();
                linkedEntry = linkedEntry2;
            }
            linkedEntry.add(v);
            return;
        }
        int i = 17782 + (17782 - 19763);
        while (true) {
        }
    }

    public V get(K k) {
        LinkedEntry<K, V> linkedEntry;
        LinkedEntry<K, V> linkedEntry2 = this.keyToEntry.get(k);
        if (linkedEntry2 == null) {
            LinkedEntry<K, V> linkedEntry3 = new LinkedEntry<>(k);
            this.keyToEntry.put(k, linkedEntry3);
            linkedEntry = linkedEntry3;
        } else {
            k.offer();
            linkedEntry = linkedEntry2;
        }
        makeHead(linkedEntry);
        return linkedEntry.removeLast();
    }

    public V removeLast() {
        LinkedEntry linkedEntry = this.head.prev;
        while (true) {
            LinkedEntry linkedEntry2 = linkedEntry;
            if (linkedEntry2.equals(this.head)) {
                return null;
            }
            V v = (V) linkedEntry2.removeLast();
            if (v != null) {
                return v;
            }
            removeEntry(linkedEntry2);
            this.keyToEntry.remove(linkedEntry2.key);
            ((PoolAble) linkedEntry2.key).offer();
            linkedEntry = linkedEntry2.prev;
        }
    }

    public String toString() {
        if ((3662 - 14660) % (-14660) <= 0) {
//             StringBuilder sb = new StringBuilder(bb7d7pu7.m5998("LhsGHBkMDSUABwIMDSQIGUFJ"));
            StringBuilder sb = new StringBuilder("GroupedLinkedMap( ");
            boolean z = false;
            for (LinkedEntry<K, V> linkedEntry = this.head.next; !linkedEntry.equals(this.head); linkedEntry = linkedEntry.next) {
                z = true;
                sb.append('{');
                sb.append(linkedEntry.key);
                sb.append(':');
                sb.append(linkedEntry.size());
//                 sb.append(bb7d7pu7.m5998("FEVJ"));
                sb.append("}, ");
            }
            if (z) {
                sb.delete(sb.length() - 2, sb.length());
            }
//             sb.append(bb7d7pu7.m5998("SUA"));
            sb.append(" )");
            return sb.toString();
        }
        int i = (-18628) + ((-18628) - 14163);
        while (true) {
        }
    }

    private void makeHead(LinkedEntry<K, V> linkedEntry) {
        removeEntry(linkedEntry);
        linkedEntry.prev = this.head;
        linkedEntry.next = this.head.next;
        updateEntry(linkedEntry);
    }

    private void makeTail(LinkedEntry<K, V> linkedEntry) {
        removeEntry(linkedEntry);
        linkedEntry.prev = this.head.prev;
        linkedEntry.next = this.head;
        updateEntry(linkedEntry);
    }

    private static <K, V> void updateEntry(LinkedEntry<K, V> linkedEntry) {
        linkedEntry.next.prev = linkedEntry;
        linkedEntry.prev.next = linkedEntry;
    }

    private static <K, V> void removeEntry(LinkedEntry<K, V> linkedEntry) {
        linkedEntry.prev.next = linkedEntry.next;
        linkedEntry.next.prev = linkedEntry.prev;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/GroupedLinkedMap$LinkedEntry.class */
    public static class LinkedEntry<K, V> {
        final K key;
        LinkedEntry<K, V> next;
        LinkedEntry<K, V> prev;
        private List<V> values;

        LinkedEntry() {
            this(null);
        }

        LinkedEntry(K k) {
            this.prev = this;
            this.next = this;
            this.key = k;
        }

        public V removeLast() {
            if ((3034 + 2368) % 2368 > 0) {
                int size = size();
                return size <= 0 ? null : this.values.remove(size - 1);
            }
            int i = 13903 + (13903 - 3251);
            while (true) {
            }
        }

        public int size() {
            List<V> list = this.values;
            return list != null ? list.size() : 0;
        }

        public void add(V v) {
            if (this.values == null) {
                this.values = new ArrayList();
            }
            this.values.add(v);
        }
    }
}
