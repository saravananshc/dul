/*     */ package com.sun.jimi.core.decoder.tiff;
/*     */ 
/*     */ import com.sun.jimi.core.JimiException;
/*     */ 
/*     */ class CCITT3d1Decomp extends Decompressor
/*     */ {
/*     */   protected byte byteSource;
/*     */   protected int bitOffset;
/*  28 */   static final int[] trailMask = { 0, 128, 192, 224, 240, 248, 252, 254, 255 };
/*  29 */   static final int[] leadMask = { 0, 127, 63, 31, 15, 7, 3, 1 };
/*     */ 
/* 191 */   static int[][] Dim1Dict = { 
/* 193 */     { 
/* 194 */     135169, 
/* 195 */     134402, 
/* 196 */     131849, 
/* 197 */     132132, 
/* 198 */     139269, 
/* 199 */     132683, 
/* 200 */     167175, 
/* 201 */     133253, 0, 
/* 203 */     133671, 
/* 204 */     133942, 
/* 205 */     134196, 
/* 206 */     1, 
/* 207 */     134689, 
/* 208 */     135049, 
/* 209 */     2, 
/* 210 */     136721, 
/* 211 */     136210, 
/* 212 */     138259, 
/* 213 */     3, 
/* 214 */     136478, 
/* 215 */     4, 
/* 216 */     137495, 
/* 217 */     141848, 
/* 218 */     5, 
/* 219 */     138010, 
/* 220 */     6, 
/* 221 */     7, 
/* 222 */     138631, 
/* 223 */     8, 
/* 224 */     142879, 
/* 225 */     9, 
/* 226 */     10, 
/* 227 */     147746, 
/* 228 */     143651, 
/* 229 */     11, 
/* 230 */     145701, 
/* 231 */     149286, 
/* 232 */     12, 
/* 233 */     141372, 
/* 234 */     141631, 
/* 235 */     13, 
/* 236 */     165419, 
/* 237 */     142636, 
/* 238 */     14, 
/* 239 */     15, 
/* 240 */     143407, 
/* 241 */     16, 
/* 242 */     17, 
/* 243 */     143945, 
/* 244 */     144302, 
/* 245 */     18, 
/* 246 */     151861, 
/* 247 */     19, 
/* 248 */     153399, 
/* 249 */     152632, 
/* 250 */     20, 
/* 251 */     146014, 
/* 252 */     146277, 
/* 253 */     21, 
/* 254 */     146765, 
/* 255 */     147048, 
/* 256 */     22, 
/* 257 */     158528, 
/* 258 */     23, 
/* 259 */     161602, 
/* 260 */     148803, 
/* 261 */     159300, 
/* 262 */     24, 
/* 263 */     149105, 
/* 264 */     25, 
/* 265 */     149620, 
/* 266 */     26, 
/* 267 */     163658, 
/* 268 */     27, 
/* 269 */     164428, 
/* 270 */     28, 
/* 271 */     151222, 
/* 272 */     151631, 
/* 273 */     29, 
/* 274 */     30, 
/* 275 */     152402, 
/* 276 */     31, 
/* 277 */     32, 
/* 278 */     153173, 
/* 279 */     33, 
/* 280 */     34, 
/* 281 */     154456, 
/* 282 */     154201, 
/* 283 */     35, 
/* 284 */     36, 
/* 285 */     154972, 
/* 286 */     37, 
/* 287 */     38, 
/* 288 */     156255, 
/* 289 */     156000, 
/* 290 */     39, 
/* 291 */     40, 
/* 292 */     156771, 
/* 293 */     41, 
/* 294 */     42, 
/* 295 */     157542, 
/* 296 */     43, 
/* 297 */     44, 
/* 298 */     158313, 
/* 299 */     45, 
/* 300 */     46, 
/* 301 */     159084, 
/* 302 */     47, 
/* 303 */     48, 
/* 304 */     159855, 
/* 305 */     49, 
/* 306 */     50, 
/* 307 */     160626, 
/* 308 */     51, 
/* 309 */     52, 
/* 310 */     161397, 
/* 311 */     53, 
/* 312 */     54, 
/* 313 */     166008, 
/* 314 */     162937, 
/* 315 */     162682, 
/* 316 */     55, 
/* 317 */     56, 
/* 318 */     163453, 
/* 319 */     57, 
/* 320 */     58, 
/* 321 */     164224, 
/* 322 */     59, 
/* 323 */     60, 
/* 324 */     164995, 
/* 325 */     61, 
/* 326 */     62, 
/* 327 */     63, 
/* 328 */     65600, 
/* 329 */     65664, 
/* 330 */     65728, 
/* 331 */     166544, 
/* 332 */     166805, 
/* 333 */     167079, 
/* 334 */     65792, 
/* 335 */     167822, 
/* 336 */     65856, 
/* 337 */     65920, 
/* 338 */     168372, 
/* 339 */     170130, 
/* 340 */     169107, 
/* 341 */     65984, 
/* 342 */     66048, 
/* 343 */     172182, 
/* 344 */     171415, 
/* 345 */     66112, 
/* 346 */     170394, 
/* 347 */     66176, 
/* 348 */     171163, 
/* 349 */     66240, 
/* 350 */     66304, 
/* 351 */     171934, 
/* 352 */     66368, 
/* 353 */     66432, 
/* 354 */     173217, 
/* 355 */     172962, 
/* 356 */     66496, 
/* 357 */     66560, 
/* 358 */     173733, 
/* 359 */     66624, 
/* 360 */     66688, 
/* 361 */     175016, 
/* 362 */     174761, 
/* 363 */     66752, 
/* 364 */     66816, 
/* 365 */     175532, 
/* 366 */     66880, 
/* 367 */     66944, 
/* 368 */     176815, 
/* 369 */     176560, 
/* 370 */     67008, 
/* 371 */     67072, 
/* 372 */     177587, 
/* 373 */     67136, 
/* 374 */     67200, 
/* 375 */     67264, 
/* 376 */     178128, 
/* 377 */     179128, 
/* 378 */     180921, 
/* 379 */     180154, 
/* 380 */     67328, 
/* 381 */     182716, 
/* 382 */     179901, 
/* 383 */     67392, 
/* 384 */     67456, 
/* 385 */     180672, 
/* 386 */     67520, 
/* 387 */     67584, 
/* 388 */     181955, 
/* 389 */     181700, 
/* 390 */     67648, 
/* 391 */     67712, 
/* 392 */     182471, 
/* 393 */     67776, 
/* 394 */     67840, 
/* 395 */     183754, 
/* 396 */     183499, 
/* 397 */     67904, 
/* 398 */     67968, 
/* 399 */     184270, 
/* 400 */     68032, 
/* 401 */     68096, 
/* 402 */     131281, 
/* 403 */     131282, 
/* 404 */     131283, 
/* 405 */     185344, 
/* 406 */     65536 }, 
/* 408 */     { 
/* 410 */     134401, 
/* 411 */     133890, 
/* 412 */     135427, 
/* 413 */     136196, 
/* 414 */     132381, 
/* 415 */     132633, 
/* 416 */     138247, 
/* 417 */     133155, 
/* 418 */     133427, 
/* 419 */     133686, 0, 
/* 421 */     135180, 
/* 422 */     1, 
/* 423 */     134671, 
/* 424 */     2, 
/* 425 */     3, 
/* 426 */     4, 
/* 427 */     135699, 
/* 428 */     5, 
/* 429 */     6, 
/* 430 */     136470, 
/* 431 */     7, 
/* 432 */     136984, 
/* 433 */     8, 
/* 434 */     9, 
/* 435 */     138010, 
/* 436 */     10, 
/* 437 */     11, 
/* 438 */     12, 
/* 439 */     138795, 
/* 440 */     139551, 
/* 441 */     140576, 
/* 442 */     13, 
/* 443 */     139816, 
/* 444 */     14, 
/* 445 */     143396, 
/* 446 */     15, 
/* 447 */     140859, 
/* 448 */     141184, 
/* 449 */     16, 
/* 450 */     145449, 
/* 451 */     158250, 
/* 452 */     17, 
/* 453 */     142518, 
/* 454 */     147757, 
/* 455 */     146990, 
/* 456 */     160047, 
/* 457 */     18, 
/* 458 */     143685, 
/* 459 */     143945, 
/* 460 */     19, 
/* 461 */     153140, 
/* 462 */     152373, 
/* 463 */     20, 
/* 464 */     155703, 
/* 465 */     21, 
/* 466 */     145740, 
/* 467 */     146013, 
/* 468 */     22, 
/* 469 */     156476, 
/* 470 */     159293, 
/* 471 */     23, 
/* 472 */     147325, 
/* 473 */     147590, 
/* 474 */     24, 
/* 475 */     161602, 
/* 476 */     160835, 
/* 477 */     168516, 
/* 478 */     25, 
/* 479 */     149134, 
/* 480 */     149575, 
/* 481 */     26, 
/* 482 */     27, 
/* 483 */     150346, 
/* 484 */     28, 
/* 485 */     29, 
/* 486 */     151629, 
/* 487 */     151374, 
/* 488 */     30, 
/* 489 */     31, 
/* 490 */     152145, 
/* 491 */     32, 
/* 492 */     33, 
/* 493 */     152916, 
/* 494 */     34, 
/* 495 */     35, 
/* 496 */     154199, 
/* 497 */     153944, 
/* 498 */     36, 
/* 499 */     37, 
/* 500 */     154715, 
/* 501 */     38, 
/* 502 */     39, 
/* 503 */     155486, 
/* 504 */     40, 
/* 505 */     41, 
/* 506 */     156257, 
/* 507 */     42, 
/* 508 */     43, 
/* 509 */     157540, 
/* 510 */     157285, 
/* 511 */     44, 
/* 512 */     45, 
/* 513 */     158056, 
/* 514 */     46, 
/* 515 */     47, 
/* 516 */     166507, 
/* 517 */     159084, 
/* 518 */     48, 
/* 519 */     49, 
/* 520 */     159855, 
/* 521 */     50, 
/* 522 */     51, 
/* 523 */     162674, 
/* 524 */     170611, 
/* 525 */     52, 
/* 526 */     161172, 
/* 527 */     161431, 
/* 528 */     53, 
/* 529 */     167288, 
/* 530 */     172921, 
/* 531 */     172154, 
/* 532 */     54, 
/* 533 */     162973, 
/* 534 */     55, 
/* 535 */     164990, 
/* 536 */     174719, 
/* 537 */     56, 
/* 538 */     166017, 
/* 539 */     164738, 
/* 540 */     57, 
/* 541 */     58, 
/* 542 */     165293, 
/* 543 */     59, 
/* 544 */     176263, 
/* 545 */     60, 
/* 546 */     168329, 
/* 547 */     61, 
/* 548 */     167051, 
/* 549 */     62, 
/* 550 */     63, 
/* 551 */     65600, 
/* 552 */     168079, 
/* 553 */     65664, 
/* 554 */     65728, 
/* 555 */     65792, 
/* 556 */     168883, 
/* 557 */     65856, 
/* 558 */     169621, 
/* 559 */     65920, 
/* 560 */     65984, 
/* 561 */     170392, 
/* 562 */     66048, 
/* 563 */     66112, 
/* 564 */     171163, 
/* 565 */     66176, 
/* 566 */     66240, 
/* 567 */     171934, 
/* 568 */     66304, 
/* 569 */     66368, 
/* 570 */     172705, 
/* 571 */     66432, 
/* 572 */     66496, 
/* 573 */     173988, 
/* 574 */     173733, 
/* 575 */     66560, 
/* 576 */     66624, 
/* 577 */     174504, 
/* 578 */     66688, 
/* 579 */     66752, 
/* 580 */     175275, 
/* 581 */     66816, 
/* 582 */     66880, 
/* 583 */     176046, 
/* 584 */     66944, 
/* 585 */     67008, 
/* 586 */     176817, 
/* 587 */     67072, 
/* 588 */     67136, 
/* 589 */     177588, 
/* 590 */     67200, 
/* 591 */     67264, 
/* 592 */     178128, 
/* 593 */     179128, 
/* 594 */     180921, 
/* 595 */     180154, 
/* 596 */     67328, 
/* 597 */     182716, 
/* 598 */     179901, 
/* 599 */     67392, 
/* 600 */     67456, 
/* 601 */     180672, 
/* 602 */     67520, 
/* 603 */     67584, 
/* 604 */     181955, 
/* 605 */     181700, 
/* 606 */     67648, 
/* 607 */     67712, 
/* 608 */     182471, 
/* 609 */     67776, 
/* 610 */     67840, 
/* 611 */     183754, 
/* 612 */     183499, 
/* 613 */     67904, 
/* 614 */     67968, 
/* 615 */     184270, 
/* 616 */     68032, 
/* 617 */     68096, 
/* 618 */     131281, 
/* 619 */     131282, 
/* 620 */     131283, 
/* 621 */     65536 } };
/*     */   static final int ABYTECOUNT = 128;
/* 656 */   static final byte[] zeroBytes_ = new byte[''];
/*     */ 
/* 668 */   static final byte[] ffBytes_ = { 
/* 669 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 670 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 671 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 672 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 673 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 674 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 675 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 676 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 678 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 679 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 680 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 681 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 682 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 683 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 684 */     -1, -1, -1, -1, -1, -1, -1, -1, 
/* 685 */     -1, -1, -1, -1, -1, -1, -1, -1 };
/*     */ 
/*     */   CCITT3d1Decomp(TiffNumberReader paramTiffNumberReader, int paramInt)
/*     */   {
/*  25 */     super(paramTiffNumberReader, paramInt);
/*     */   }
/*     */ 
/*     */   public void begOfStrip()
/*     */   {
/* 109 */     this.bitOffset = 0;
/*     */   }
/*     */ 
/*     */   public int decode1DWord(byte paramByte)
/*     */     throws JimiException
/*     */   {
/*  71 */     int[] arrayOfInt = Dim1Dict[paramByte];
/*  72 */     int i = 0;
/*  73 */     int j = arrayOfInt[0];
/*     */     do
/*     */     {
/*  76 */       if ((this.bitOffset++ & 0x7) == 0)
/*  77 */         this.byteSource = readByte();
/*     */       else {
/*  79 */         this.byteSource = ((byte)(this.byteSource << 1));
/*     */       }
/*  81 */       if ((this.byteSource & 0x80) != 0)
/*  82 */         i = (j & 0xFF00) >>> 8;
/*     */       else {
/*  84 */         i = j & 0xFF;
/*     */       }
/*  86 */       if (i == 0) {
/*  87 */         throw new JimiException("Code Not Found In Dict");
/*     */       }
/*  89 */       j = arrayOfInt[i];
/*  90 */     }while ((j & 0x20000) != 0);
/*     */ 
/*  92 */     return j;
/*     */   }
/*     */ 
/*     */   public void decodeLine(byte[] paramArrayOfByte, int paramInt)
/*     */     throws JimiException
/*     */   {
/* 124 */     byte b = 0;
/*     */ 
/* 126 */     int j = 0;
/*     */ 
/* 129 */     setArrayZero(paramArrayOfByte);
/* 130 */     if (this.invertOut_)
/*     */     {
/* 132 */       while (j < paramInt)
/*     */       {
/* 134 */         int i = getRunLength(b);
/* 135 */         if (b == 0)
/* 136 */           outputBitRun(paramArrayOfByte, j, i);
/* 137 */         j += i;
/* 138 */         b = (byte)(b ^ 0x1);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 143 */       while (j < paramInt)
/*     */       {
/* 145 */         int i = getRunLength(b);
/* 146 */         if (b != 0)
/* 147 */           outputBitRun(paramArrayOfByte, j, i);
/* 148 */         j += i;
/* 149 */         b = (byte)(b ^ 0x1);
/*     */       }
/*     */     }
/*     */ 
/* 153 */     endOfLine();
/*     */   }
/*     */ 
/*     */   public void endOfLine()
/*     */   {
/* 117 */     if ((this.bitOffset & 0x7) != 0)
/* 118 */       this.bitOffset = 0;
/*     */   }
/*     */ 
/*     */   public int getRunLength(byte paramByte)
/*     */     throws JimiException
/*     */   {
/*  98 */     int j = 0;
/*     */     int i;
/*     */     do
/*     */     {
/* 101 */       i = decode1DWord(paramByte);
/* 102 */       j += (i & 0xFFFF);
/* 103 */     }while ((i & 0x10000) != 0);
/* 104 */     return j;
/*     */   }
/*     */ 
/*     */   public void outputBitRun(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  40 */     if (paramInt2 <= 0) {
/*  41 */       return;
/*     */     }
/*  43 */     int i = paramInt1 >> 3;
/*  44 */     int j = paramInt1 & 0x7;
/*     */ 
/*  46 */     if (j != 0)
/*     */     {
/*  48 */       if (paramInt2 < 8 - j)
/*     */       {
/*     */         int tmp33_31 = i;
/*     */         byte[] tmp33_30 = paramArrayOfByte; tmp33_30[tmp33_31] = ((byte)(tmp33_30[tmp33_31] | trailMask[paramInt2] >> j));
/*  51 */         return;
/*     */       }
/*  53 */       paramInt2 -= 8 - j;
/*     */       int tmp61_58 = (i++);
/*     */       byte[] tmp61_55 = paramArrayOfByte; tmp61_55[tmp61_58] = ((byte)(tmp61_55[tmp61_58] | leadMask[j]));
/*     */     }
/*     */ 
/*  58 */     while (paramInt2 >= 8)
/*     */     {
/*  60 */       paramArrayOfByte[(i++)] = -1;
/*  61 */       paramInt2 -= 8;
/*     */     }
/*     */ 
/*  64 */     if (paramInt2 != 0)
/*     */     {
/*     */       int tmp99_97 = i;
/*     */       byte[] tmp99_96 = paramArrayOfByte; tmp99_96[tmp99_97] = ((byte)(tmp99_96[tmp99_97] | trailMask[paramInt2]));
/*     */     }
/*     */   }
/*     */ 
/*     */   static final void setArrayFF(byte[] paramArrayOfByte)
/*     */   {
/* 642 */     int i = paramArrayOfByte.length;
/* 643 */     int j = 0;
/* 644 */     while (i >= 128)
/*     */     {
/* 646 */       System.arraycopy(ffBytes_, 0, paramArrayOfByte, j, 128);
/* 647 */       j += 128;
/* 648 */       i -= 128;
/*     */     }
/* 650 */     if (i > 0)
/* 651 */       System.arraycopy(ffBytes_, 0, paramArrayOfByte, j, i);
/*     */   }
/*     */ 
/*     */   static final void setArrayZero(byte[] paramArrayOfByte)
/*     */   {
/* 628 */     int i = paramArrayOfByte.length;
/* 629 */     int j = 0;
/* 630 */     while (i >= 128)
/*     */     {
/* 632 */       System.arraycopy(zeroBytes_, 0, paramArrayOfByte, j, 128);
/* 633 */       j += 128;
/* 634 */       i -= 128;
/*     */     }
/* 636 */     if (i > 0)
/* 637 */       System.arraycopy(zeroBytes_, 0, paramArrayOfByte, j, i);
/*     */   }
/*     */ }

/* Location:           dulux-signed.jar
 * Qualified Name:     com.sun.jimi.core.decoder.tiff.CCITT3d1Decomp
 * JD-Core Version:    0.6.2
 */